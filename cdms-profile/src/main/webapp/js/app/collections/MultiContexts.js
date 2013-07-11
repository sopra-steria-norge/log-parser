var app = app || {};

app.MultiContexts = Backbone.Collection.extend({
	model: app.MultiContext,
	url: '/rest/multicontexts/'
});