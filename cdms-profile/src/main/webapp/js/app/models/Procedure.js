var app = app || {};

app.Procedure = Backbone.RelationalModel.extend({
	idAttribute: '_id',
    urlRoot: '/rest/procedure/',
	initialize: function(options){

	},
});