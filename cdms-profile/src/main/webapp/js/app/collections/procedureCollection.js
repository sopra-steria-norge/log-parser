app.ProcedureCollection = Backbone.Collection.extend({
	model: app.Procedure,
	url: 'rest/procedure/'
});