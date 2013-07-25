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
	}
})