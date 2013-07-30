var app = {
	collections: {},
	models : {},
	views : {},

	initialize : function() {
		app.collections.procedures = new app.ProcedureCollection();
		app.collections.procedures.fetch({success: function(){
			app.router = new app.Router();
			Backbone.history.start();
		}});

	}
}


$(document).ready(function() {
	app.initialize();
});

app.xhrs = [];
app.percentiles = [100, 95, 90, 80, 50, 0];
app.graphOfs= [[14],[18],[17]];
app.nrOfBuckets = 200;
app.percentileLimits = {
	 1: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	 2: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	 3: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	 4: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	 5: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	 6: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	 7: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	 8: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	 9: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	10: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	11: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	12: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	13: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	14: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	15: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	16: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	17: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	18: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	19: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	20: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	21: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	22: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	23: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	24: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	25: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	26: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	27: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	28: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	29: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	30: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	31: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	32: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},
	33: {100: "PT1S", 95:"PT1S", 90:"PT1S", 80:"PT1S", 50:"PT1S", 0:"PT1S"},

}