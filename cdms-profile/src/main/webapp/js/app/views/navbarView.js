app.NavbarView = Backbone.View.extend({

	initialize: function(options){
		this.listenTo(options.router, 'route', this.showNavbar)
	},

	events: {
		'click #nav a' : 'showNavbar'
	},

	showNavbar: function(page) {
		this.$el.find('.navbar').remove();
		this.$el.append(templates.navbar);
		this.$el.find('#'+ this.model.get('selectedView')).addClass('active');
		
	}

});