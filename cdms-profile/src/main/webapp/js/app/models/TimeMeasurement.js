var app = app || {};

app.TimeMeasurement = Backbone.RelationalModel.extend({
	relations: [{
		type: Backbone.HasOne,
		key: 'procedureID',
		relatedModel: 'Procedure',
		collectionType: 'Procedures',
		reverseRelation: {
			type: Backbone.HasMany,
			key: 'timeMeasurementID'

		}
	},
	{
		type: Backbone.HasOne,
		key: 'multiContextID',
		relatedModel: 'MultiContext',
		collectionType: 'MultiContexts',
		reverseRelation: {
			type: Backbone.HasMany,
			key: 'timeMeasurementID'
		}
	}],

	initialize: function(options){

	},

	idAttribute: 'id',
	urlRoot: '/rest/timemeasurement/',
	url: function(){

	}
});