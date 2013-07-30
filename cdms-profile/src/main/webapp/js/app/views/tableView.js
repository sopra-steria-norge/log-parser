app.TableView = Backbone.View.extend({
	
	initialize : function () {
		return this;
	},

	render: function () {
		this.$el.find('thead').first().append(this.createTableHead());
		this.$el.find('tbody').first().append(this.createTableBody());
		this.updateColors();
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
		app.collections.procedures.each(function (pro){
			body += '<tr data-tableof="'+ pro.id+'">';
			if (_.flatten(app.graphOfs).indexOf(pro.id) !== -1){
				body += '<td>' + pro.get('name') + ' <i class="icon-eye-open"></i></td>';
			}
			else{
				body += '<td>' + pro.get('name') + '</td>';
			}
			_.each(app.percentiles, function (per) {
				body += '<td data-percentile="'+per+'">' + app.collections.percentileCollection.get(pro.id)['attributes']['percentiles'][per] + '</td>';
			});
			body += '</tr>';
		})
		return body;
	},

	updateColors: function () {
		_.each(this.$el.find("tbody>tr"), function (tr) {
			tr = $(tr);
			var id = tr.data('tableof');
			_.each(tr.find("td"), function (td){
				td = $(td);
				var per = td.data('percentile');
				if(typeof per !== 'undefined'){
					if (typeof app.percentileLimits[id] !== 'undefined') {
						if (typeof app.percentileLimits[id][per] !== 'undefined') {
							var actual = moment.duration(td[0].innerHTML);
							var limit = moment.duration(app.percentileLimits[id][per]);
							td.addClass('success');
							if (actual._milliseconds > limit._milliseconds) {
								td.removeClass('success');
								td.addClass('error');
								tr.find("td").first().removeClass('success');
								tr.find("td").first().addClass('error');
							}
						};
					};
				}
				else if (typeof app.percentileLimits[id] !== 'undefined'){

					td.addClass('success');
				}
			})
		})
	}
})