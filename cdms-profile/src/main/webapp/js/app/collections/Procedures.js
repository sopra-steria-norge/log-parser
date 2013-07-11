var app = app || {};

app.Procedures = Backbone.Collection.extend({
	model: app.Measured
});