app.TableView = Backbone.View.extend({
    initialize: function() {
        return this;
    },
    render: function() {
        this.$el.find('thead').first().append(this.createTableHead());
        this.$el.find('tbody').first().append(this.createTableBody());
        this.updateColors();
    },
    createTableHead: function() {
        head = '<tr>' +
                '	<th>Name/Percentiles</th>';
        _.each(app.percentiles, function(p) {
            head += '	<th>' + p + '</th>';
        });
        head += '</tr>';
        return head;
    },
    createTableBody: function() {
        var that = this;
        body = '';
        app.collections.procedureCollection.each(function(pro) {
            body += '<tr data-tableof="' + pro.id + '">';
            if (_.flatten(app.graphOfs).indexOf(pro.id) !== -1) {
                body += '<td>' + app.collections.procedureCollection.getProcedureName(pro.id) + ' <i class="icon-eye-open"></i></td>';
            }
            else {
                body += '<td>' + app.collections.procedureCollection.getProcedureName(pro.id) + '</td>';
            }
            _.each(app.percentiles, function(per) {
                body += '<td data-percentile="' + per + '">' + app.collections.percentileCollection.get(pro.id)['attributes']['percentiles'][per] + '</td>';
            });
            body += '</tr>';
        })
        return body;
    },
    updateColors: function() {
        _.each(this.$el.find("tbody>tr"), function(tr) {
            tr = $(tr);
            var id = tr.data('tableof');
            _.each(tr.find("td"), function(td) {
                td = $(td);
                var per = td.data('percentile');
                td.addClass('success');
                if (typeof per !== 'undefined') {
                    var actual = moment.duration(td.html());
                    var limit = moment.duration("PT2S");
                    
                    if (typeof app.percentileLimits[id] !== 'undefined') {
                        if (typeof app.percentileLimits[id][per] !== 'undefined') {
                            limit = moment.duration(app.percentileLimits[id][per]);
                        }
                    }
                    
                    if (actual._milliseconds > limit._milliseconds) {
                        td.removeClass('success').addClass('error');
                        tr.find("td").first().removeClass('success').addClass('error');
                    }
                }
            })
        })
    },
    /*
    _getProcedureName: function(procedure) {
        function isValid(str) {
            var ans = typeof str !== 'undefined' && str !== null;
            return ans;
        }
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
    */
})