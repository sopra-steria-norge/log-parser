var app = {
	collections: {},
	models : {},
	views : {},

	initialize : function() {
		app.router = new app.Router();
		Backbone.history.start();

	}
}
$(document).ready(function() {
	app.initialize();
});
