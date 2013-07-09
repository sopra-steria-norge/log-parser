var app = app || {};

app.MultiContext = Backbone.RelationalModel.extend({
	initialize: function(options) {

	},

	idAttribute: 'id',
	urlRoot: '/rest/multicontext/',
	url: function(){
		// TODO: Define url
		return "SomeURL"
	}
});