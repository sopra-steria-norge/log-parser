var app = app || {};

app.TimeMeasurements = Backbone.Collection.extend({
	model: app.TimeMeasurement,
	url: "/rest/",

});