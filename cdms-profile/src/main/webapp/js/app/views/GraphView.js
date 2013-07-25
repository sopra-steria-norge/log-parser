app.GraphView = Backbone.View.extend({

	initialize : function () {
		this.container = this.$el;
		this.svgcontainer = undefined;
        this.graphics = undefined;
        this.series;
        this.graphOf = [this.$el.data('graphof')];
        if (typeof this.graphOf[0] === 'string') {
            this.graphOf = this.graphOf[0].split(',')
            for (var i = 0; i < this.graphOf.length; i++) {
                this.graphOf[i] = parseInt(this.graphOf[i]);
            };
        };

        this.numberOfBuckets = 200;

        this.procedureMapping;
        this.getProcedureMapping(function() {

        }.bind(this));
	},

	render: function() {
        this.container = this.$el;
        this.svgcontainer = undefined;
        this.graphics = undefined;
        this.series;
        this.intervalUpdater;
        this.isModal = false;
        this.isShown = false;
        this.placeholder;
        this.containerwidth;
        this.procedureMapping;

        this.getProcedureMapping(function() {
            this.svgcontainer = this.container;
            //this.createModalIfActivated(this.json);
            this.graphics = this.drawGraph(this.svgcontainer);
            this.startUpdate();
            this.createResizeHandler();
            this.createDestroyHandler();
        }.bind(this));
    },
    getProcedureMapping: function(callback) {
        var that = this;
        $.get('rest/procedure', function(resp) {
            that.procedureMapping = resp;
            callback();
        });
    },
    destroy_view: function() {
        this.undelegateEvents();
        this.$el.removeData().unbind();
        this.stopListening();
        this.remove();
        Backbone.View.prototype.remove.call(this);
        if (typeof this.intervalUpdater !== 'undefined') {
            clearInterval(this.intervalUpdater);
        }
    },
    startUpdate: function() {
        var timeconfig = this.timeConfig();
        if (timeconfig.realtime) {
            this.intervalUpdater = setInterval(function() {
                this.updateGraph();
            }.bind(this), 100);
        } else {
            this.updateGraph();
        }
    },
    updateGraph: function() {
        var that = this;
        var name = this.graphOf;
        console.log('name', name)
        var tconf = this.timeConfig();
        var intervalConf = tconf.pt.join('/');
        var interval = new moment().interval(intervalConf);
        var from = interval.start().toISOString();
        var to = interval.end().toISOString();
        var suffix = '?from=' + from + '&to=' + to + '&buckets=' + this.numberOfBuckets;
        for (var i = 0; i < name.length; i++) {
            var url = 'rest/timeMeasurement/' + name[i] + suffix;
            $.get(url, function(resp) {
                console.debug('TMResp', resp);
                update(resp);
            }.bind(this));
        }
        function update(newdata) {
            var procedureid = -1;
            for (var i = 0; i < newdata.length; i++) {
                if (typeof newdata[i] !== 'undefined') {
                    procedureid = newdata[i].procedure.id;
                    break;
                }
            }
            if (procedureid === -1) {
                return;
            }
            var s = undefined;
            var remI = -1;
            for (var i = 0; i < that.series.length; i++) {
                if (that.series[i].procedureid === procedureid) {
                    s = that.series[i];
                    remI = i;
                    break;
                }
            }
            if (typeof s === 'undefined') {
                return;
            }
            console.debug('pushing', newdata, 'on', s);
            s.data.push(newdata);
            var nd;
            while (typeof (nd = newdata.shift()) !== 'undefined') {
                if (nd === null) {
                    continue;
                }
                s.data.push(that.parseTimestamp(nd));
            }
            while (s.data.length > that.numberOfBuckets) {
                s.data.shift();
            }
            console.debug('s.length', s.data.length, s);
            that.series[remI] = s;


            that.graphics.setData(that.series);
            that.graphics.setupGrid();
            that.graphics.draw();
            
            console.debug('gr', that.graphics);
            
        }
    },
    parseTimestamp: function(timestamp) {
        var time = moment(timestamp.jodaTimestamp).valueOf();
        var duration = moment.duration(timestamp.duration)._milliseconds;
        return [time, duration];
    },
    createDestroyHandler: function() {
        $('body').on('destroy_view', function() {
            this.destroy_view();
        }.bind(this));
    },
    createResizeHandler: function() {
        var that = this;
        $(window).on('resize', function() {
            if (typeof this.svgcontainer === 'undefined') {
                return;
            } else {
                this.resize();
            }
        }.bind(this));
    },
    resize: function() {
        if (this.isModal) {
            var c = this.placeholder;
            var nw = c.width();
        } else {
            var c = this.svgcontainer;
            var nw = this.svgcontainer.parent().width() ? this.svgcontainer.parent().width() : this.containerwidth;
        }
        var maxheight = 600;
        this.svgcontainer.height(nw < maxheight ? nw : maxheight);
        this.svgcontainer.width(nw);
        this.graphics.resize();
        this.graphics.setupGrid();
        this.graphics.draw();
    },
    createClickHandler: function() {
        var that = this;
        var modal = $('body>.modal');
        this.placeholder = that.svgcontainer.clone(false, false);
        this.svgcontainer.on('click', 'canvas', function(event) {
            if (that.isModal) {
                event.preventDefault();
                return false;
            }
            that.isModal = true;
            that.placeholder.removeClass().addClass('span12');
            that.placeholder.append(that.svgcontainer.children());
            var modalheader = that.graphOf.join('/');
            var modalbody = that.placeholder;
            modal.find('.modal-header>h3').html(modalheader);
            modal.find('.modal-body').html(modalbody);
            modal.bigmodal('show');
            modal.on('shown', function() {
                $(document).trigger('resize');
                that.isShown = true;
            });
            modal.on('hide', function(e) {
                if (!that.isShown) {
                    e.preventDefault();
                    return false;
                }
                that.svgcontainer.append(that.placeholder.children());
                that.placeholder.remove();
                that.isModal = false;
                that.isShown = false;
                $(document).trigger('resize');
            });
            modal.on('hidden', function() {
                modal.off();
            });
        });
    },
    
    createModalIfActivated: function(json) {
        if (json.data.modal) {
            if ($('body>.modal').length === 0) {
//No modal handler found, ask to initialize one
                new PageView({model: new PageComponentCollection([{type: 'modal'}]), el: 'body'});
            }
            this.createClickHandler();
        }
    },
    drawGraph: function(svgcontainer) {
        if (this.svgcontainer.length === 0) {
            throw "No svgcontainer found";
        }
        svgcontainer.html('');

        if (typeof this.graphics !== 'undefined') {
            this.series = this.graphics.series;
        } else {
            this.series = [];
            for (var i = 0; i < this.graphOf.length; i++) {
                var serie = {
                    data: [],
                    procedureid: this.graphOf[i],
                    label: this.getNameFromProcedureId(this.graphOf[i])
                };
                this.series.push(serie);
            }
        }
        var maxheight = 600;
        svgcontainer.height(svgcontainer.width() < maxheight ? svgcontainer.width() : maxheight);
        var graph = $.plot(svgcontainer[0], this.series, this.graphOptions());
        return graph;
    },
    getNameFromProcedureId: function(id) {
        for (var i = 0; i < this.procedureMapping.length; i++) {
            if (id === this.procedureMapping[i].id) {
                return createNameFromProcedure(this.procedureMapping[i]);
                break;
            }
        }
        return 'Unknown';

        function createNameFromProcedure(procedure) {
            var name = 'Unknown';
            if (procedure.name) {
                name = procedure.name;
            }
            return name;
        }
    },

	graphOptions: function() {
		return {
                series: {
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                grid: {
                    hoverable: true
                },
                xaxis: {
                    mode: 'time'
                },
                tooltip: true
            }
	},

    timeConfig: function() {
        return {
            realtime: false,
            pollInterval: 1000,
            pt: ['PT24H/']
        }
    }
})