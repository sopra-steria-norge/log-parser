var app = app || {};

app.TimeMeasurement = Backbone.RelationalModel.extend({
	idAttribute: '_id',
    urlRoot: '/rest/timemeasurement/',
	relations: [{
		type: Backbone.HasOne,
		key: 'procedureID',
		relatedModel: 'app.Procedure',
		collectionType: 'app.Procedures',
		reverseRelation: {
			type: Backbone.HasMany,
			key: 'timeMeasurement',
			includeInJSON: '_id'

		}
	},
	{
		type: Backbone.HasOne,
		key: 'multiContextID',
		relatedModel: 'app.MultiContext',
		collectionType: 'app.MultiContexts',
		reverseRelation: {
			type: Backbone.HasMany,
			key: 'timeMeasurement'
			includeInJSON: '_id'
		}
	}],

	initialize: function(options){

	},

	url: function(){

	}
});