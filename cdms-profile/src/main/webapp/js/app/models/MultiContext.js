var app = app || {};

app.MultiContext = Backbone.RelationalModel.extend({
	idAttribute: '_id',
    urlRoot: '/rest/multicontext/',
	initialize: function(options) {

	},

	url: function(){
		// TODO: Define url
		return "SomeURL"
	}
});