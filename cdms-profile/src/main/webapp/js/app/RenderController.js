var RenderController = function(args)
{
	this.initialize = function()
	{
		this.element = args.element;
		this.graph = args.graph;

		this.renderer = this.element[0];
		this.offset = this.element[1];
		this.interpolation = this.element[2];

		this.config = {
			renderer: 'area',
			offset: 'zero',
			interpolation: 'cardinal',
			unstack: false
		};

		this.renderer.addEventListener('change', function(e){

			$(this.offset).find('input').prop('disabled', false); // enables all offset radio buttons
			$(this.interpolation).find('input').prop('disabled', false); // enables all interpolation radio buttons

        	if(e.srcElement.value == 'area')
        	{
				$(this.offset).find('#stack').click();
				$(this.interpolation).find('#cardinal').click();

        	}
        	else if( e.srcElement.value == 'bar')
        	{
                $(this.offset).find('#stack').click();

				// disables all interpolation radio button as it has no effect on bar graphs
                $(this.interpolation).find('input').prop('disabled', true);

        	}
        	else if(e.srcElement.value == 'line')
        	{
				$(this.offset).find('#value').click();
				$(this.interpolation).find('#cardinal').click();

				// disables stack and stream offset for line
				$(this.offset).find('#stack').prop('disabled', true);
				$(this.offset).find('#stream').prop('disabled', true);

        	}
			this.config.renderer = e.srcElement.value;
			this.graph.configure(this.config);
        	this.graph.render();
        }.bind(this), false);

        this.offset.addEventListener('change', function(e)
        {
        	this.config.offset = e.srcElement.value;
        	this.config.unstack = false;
			if(e.srcElement.value == 'value') {this.config.unstack = true;}
			this.graph.configure(this.config);
            this.graph.render();

        }.bind(this), false);

        this.interpolation.addEventListener('change', function(e)
        {
        	this.config.interpolation = e.srcElement.value;
        	this.graph.configure(this.config);
            this.graph.render();

        }.bind(this), false);
	};

	this.initialize();
};