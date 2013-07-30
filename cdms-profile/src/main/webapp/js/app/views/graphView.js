app.GraphView = Backbone.View.extend({

	initialize : function () {
		this.container = this.svgcontainer = this.$el;
        this.graphics = undefined;
        this.series;
        this.graphOf = [this.$el.data('graphof')];
        if (typeof this.graphOf[0] === 'string') {
            this.graphOf = this.graphOf[0].split(',')
            for (var i = 0; i < this.graphOf.length; i++) {
                this.graphOf[i] = parseInt(this.graphOf[i]);
            };
        };

	},

	render: function() {
        this.graphics = this.drawGraph(this.svgcontainer);
        this.createResizeHandler();
        this.createDestroyHandler();
    },

    destroy_view: function() {
        $(window).off('resize');
        $('body').off('destroy_view');
        this.undelegateEvents();
        this.$el.removeData().unbind();
        this.stopListening();
        this.remove();
        Backbone.View.prototype.remove.call(this);
        if (typeof this.intervalUpdater !== 'undefined') {
            clearInterval(this.intervalUpdater);
        }
    },
    parseTimestamp: function(timestamp) {
        var time = moment(timestamp.get('jodaTimestamp')).valueOf();
        var duration = moment.duration(timestamp.get('duration'))._milliseconds;
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
            var grouped = _.groupBy(app.collections.measurementCollection.models, function(mb){
                return mb.get('procedureId');
            });
            var that = this;
            _.each(this.graphOf, function (id) {
                var serie = {
                    data: [],
                    procedureid: id,
                    label: that.getNameFromProcedureId(id)
                };
                _.each(grouped[id], function (measurment){
                    serie.data.push(that.parseTimestamp(measurment));
                });
                that.series.push(serie);
            });
        }
        var maxheight = 600;
        svgcontainer.height(svgcontainer.width() < maxheight ? svgcontainer.width() : maxheight);
        var graph = $.plot(svgcontainer[0], this.series, this.graphOptions());
        return graph;
    },
    getNameFromProcedureId: function(id) {
        if (typeof app.collections.procedures.get(id) !== 'undefined') {
            return app.collections.procedures.get(id).get('name');
        };
        return 'Unknown';
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
	}
})