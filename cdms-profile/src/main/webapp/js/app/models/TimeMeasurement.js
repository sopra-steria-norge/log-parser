var app = app || {};

app.TimeMeasurement = Backbone.RelationalModel.extend({
	idAttribute: '_id',
    urlRoot: '/rest/',
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
			key: 'timeMeasurement',
			includeInJSON: '_id'
		}
	}],

	defaults: {
		id: 0,
		procedure: null,
		multicontext: null,
		timestamp: 0,
		duration: 0
	},

	initialize: function(options){
		console.log(options)
	},

});