app.NavbarModel = Backbone.Model.extend({

	initialize : function(options) {
		this.set({'selectedView' : ''})
	},

	getCurrentViewTemplate : function(){
		var view = this.get('selectedView');

		switch (view) {
			case 'last24h':
				return '<h1>'+view+'</h1>'+templates.defaultPage();
			case 'last72h':
				return '<h1>'+view+'</h1>'+templates.defaultPage();
			case 'last1w':
				return '<h1>'+view+'</h1>'+templates.defaultPage();
			case 'last2w':
				return '<h1>'+view+'</h1>'+templates.defaultPage();
		}
	},

	getCurrentTimeConfig : function () {
		var view = this.get('selectedView');

		switch (view) {
			case 'last24h':
				return {
            		realtime: false,
            		pollInterval: 1000,
            		pt: ['PT24H/']
        		}
			case 'last72h':
				return {
            		realtime: false,
            		pollInterval: 1000,
            		pt: ['PT72H/']
        		}
			case 'last1w':
				return {
            		realtime: false,
            		pollInterval: 1000,
            		pt: ['P1W/']
        		}
			case 'last2w':
				return {
            		realtime: false,
            		pollInterval: 1000,
            		pt: ['P2W/']
        		}
		}
	}
})