(function(){
	var legend = function(container, json){
		legend.render(container, json)
	}
	legend.render = function(container, json){
		var myDiv = document.createElement('div');
		PageBuilder.setAttribute(myDiv, 'class', json.classes);
		PageBuilder.setAttribute(myDiv, 'id', json.id);
		container.append(myDiv);
	}
	PageBuilder.extensions.legend = legend;
});