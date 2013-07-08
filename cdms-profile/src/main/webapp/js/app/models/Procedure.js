var app = app || {};

app.Measured = Backbone.Model.extend({

	initialize: function(options){

	},

	idAttribute: 'id',
	url: function(){
		// TODO: Define url
		return "SomeURL"
	}

});