app.TableView = Backbone.View.extend({
	
	initialize : function () {
		return this;
	},

	render: function () {
		this.$el.find('thead').first().append(this.createTableHead());
		this.$el.find('tbody').first().append(this.createTableBody());
	},

	createTableHead: function (){
		head = '<tr>'+
		'	<th>Name/Percentiles</th>';
		_.each(app.percentiles, function (p) {
			head += '	<th>' + p + '</th>';
		});
		head += '</tr>';
		return head;
	},

	createTableBody: function () {
		body = '';
		/*
		_.each(app.collections.procedures, function(pro){
			console.log(pro);
			body += '<tr>' + pro.get('name') + '</tr>';
			body += '<td>' + pro.get('name') + '</td>';
		})
		*/
	}
})