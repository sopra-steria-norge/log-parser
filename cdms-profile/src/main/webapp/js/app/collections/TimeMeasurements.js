var app = app || {};

app.TimeMeasurements = Backbone.Collection.extend({
	model: app.Measured
});