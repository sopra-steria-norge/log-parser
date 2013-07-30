app.ProcedureCollection = Backbone.Collection.extend({
	model: app.Procedure,
	url: 'rest/procedure/',

	getProcedureName: function(id) {
        function isValid(str) {
            var ans = typeof str !== 'undefined' && str !== null;
            return ans;
        }
        var procedure = this.get(id)
        if (isValid(procedure.get('name'))) {
            return procedure.get('name');
        } else {
            var out = procedure.get('className');
            if (isValid(procedure.get('method'))) {
                out += "." + procedure.get('method');
            }
            return out;
        }
    }
});