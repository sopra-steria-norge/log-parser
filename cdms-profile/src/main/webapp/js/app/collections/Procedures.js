app.Procedures = Backbone.Collection.extend({
	model: app.Procedure,
	url: 'rest/procedure/'
});