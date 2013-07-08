var app = app || {};

app.MultiContext = Backbone.RelationalModel.extend({

	initialize: function(options) {

	},

	idAttribute: 'id'

	url: function(){
		// TODO: Define url
		return "SomeURL"
	}
});