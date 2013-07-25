app.PageView = Backbone.View.extend({

	initialize : function (options) {
		this.navbar = app.navbarView.model;
		return this;
	},

	remove: function() {
		this.$el.empty();
	},

	render: function() {
		this.$el.append(this.navbar.getCurrentViewTemplate());
		_.each(this.$el.find('.graph'), function(element) {
			var graph = new app.GraphView({
				el: $(element)
			});
			graph.render();
		});
	}


})