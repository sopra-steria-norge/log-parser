app.Router = Backbone.Router.extend({
	routes: {
		'': 'showpageRoute',
		'page/:page': 'showpageRoute',
		'*actions': 'showpageRoute'
    },

	initialize : function() {
		this.navbarModel = new app.NavbarModel({selectedView: ''})
		app.navbarView = this.navbarView = new app.NavbarView({
			el: '#nav',
			router: this,
			model: this.navbarModel
		});
		this.listenTo(this.navbarModel, 'change:selectedView', this.viewPage)
	},

    showpageRoute: function(page) {
    	if(typeof page === 'undefined'){
    		this.navbarModel.set({'selectedView' : 'last24h'})
    	}
    	else{
    		this.navbarModel.set({'selectedView' : page})
    	}
    },

    viewPage: function() {
    	if(this.activeView) {
    		this.activeView.remove();
    	}

        var tconf = this.navbarModel.getCurrentTimeConfig();
        var intervalConf = tconf.pt.join('/');
        var interval = new moment().interval(intervalConf);
        var from = interval.start().toISOString();
        var to = interval.end().toISOString();
        var that = this;
        app.collections.measurementBuckets = new app.MeasurementBuckets();
        app.collections.measurementBuckets.fetch({
            data: $.param({from:from, to:to, buckets:app.nrOfBuckets}),
            success:function(){
            	that.activeView = new app.PageView({
            		el: '#appcontainer'
            	});
            	that.activeView.render();
        }});


    }
})