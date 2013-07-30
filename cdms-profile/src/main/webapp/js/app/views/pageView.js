app.PageView = Backbone.View.extend({

	initialize : function (options) {
		this.$el.append(app.models.navbar.getCurrentViewTemplate());
		return this;
	},

	remove: function() {
        $('body').trigger('destroy_view');
        this.$el.empty();
	},

	render: function() {

		var tconf = app.models.navbar.getCurrentTimeConfig();
        var intervalConf = tconf.pt.join('/');
        var interval = new moment().interval(intervalConf);
        var from = interval.start().toISOString();
        var to = interval.end().toISOString();
        var that = this;
        if(this.$el.find('.graph').length !== 0) {
            app.collections.measurementCollection = new app.MeasurementCollection();
            var xhr = app.collections.measurementCollection.fetch({
                data: $.param({from:from, to:to, buckets:app.nrOfBuckets}),
                success:function(){
    				_.each(that.$el.find('.graph'), function(element) {
    					var graph = new app.GraphView({
    						el: $(element)
    					});
    					graph.render();
    				});
            	}
        	});
            app.xhrs.push(xhr);
        }
        if(this.$el.find('.table').length !== 0) {
            app.collections.percentileCollection = new app.PercentileCollection();
            var xhr = app.collections.percentileCollection.fetch({
            	data: $.param({from:from, to:to, percentages:app.percentiles+''}),
            	success:function () {
            		var table = new app.TableView({
            			el: that.$el.find('.table').first()
            		});
            		table.render();
            	}
            });
            app.xhrs.push(xhr);
        }
	}


})