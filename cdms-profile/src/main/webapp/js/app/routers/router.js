app.Router = Backbone.Router.extend({
	routes: {
		'': 'showpageRoute',
		'page/:page': 'showpageRoute',
		'*actions': 'showpageRoute'
    },

	initialize : function() {
		app.models.navbar = new app.Navbar({selectedView: ''})
		app.navbarView = this.navbarView = new app.NavbarView({
			el: '#nav',
			router: this,
			model: app.models.navbar
		});
		this.listenTo(app.models.navbar, 'change:selectedView', this.viewPage)
	},

    showpageRoute: function(page) {
    	if(typeof page === 'undefined'){
    		app.models.navbar.set({'selectedView' : 'last24h'})
    	}
    	else{
    		app.models.navbar.set({'selectedView' : page})
    	}
    },

    viewPage: function() {
    	if(this.activeView) {
    		this.activeView.remove();
    	}
        this.activeView = new app.PageView({
            el: '#appcontainer'
        });
        this.activeView.render();
        
    }
})