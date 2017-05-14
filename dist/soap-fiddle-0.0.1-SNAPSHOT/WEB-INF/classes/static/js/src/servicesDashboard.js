/**
 * 
 */
$(document).ready(function() {
	
	var getServiceStatus = function(componentIdentifier) {
		if(componentIdentifier){
			$('#'+componentIdentifier).removeClass('success warning danger').find('.js-component-response').html('Requesting Status...');
			$('#'+componentIdentifier).find('.js-component-timer').html("");
			var t0 = Date.now();
			$.ajax({
		        url: "/servicesDashboard/getServiceStatus",
		        dataType: "json",
		        data: {componentIdentifier: componentIdentifier}
		    }).then(function(response) {
		    	if(response && response.successResponse!="false"){
		    		$('#'+componentIdentifier).addClass('success').find('.js-component-response').html('Success');
		    		$('#'+componentIdentifier).find('.js-component-response').addClass('btn-success').popover({title:'Response',content: response.soapResponse}); 
		    	} else if(response){
		    		$('#'+componentIdentifier).addClass('warning').find('.js-component-response').html('Invalid Response');
		    		$('#'+componentIdentifier).find('.js-component-response').addClass('btn-warning').popover({title:'Response',content: response.soapResponse}); 
		    	}
		    	$('#'+componentIdentifier).find('.js-component-timer').html(Date.now()-t0 + '&nbsp;ms');
		    }).fail(function(response) {
		    	if(response){
		    		$('#'+componentIdentifier).addClass('danger').find('.js-component-response').html('Failure');
		    		$('#'+componentIdentifier).find('.js-component-response').addClass('btn-danger').popover({title:'Response',content: JSON.stringify(response.responseJSON,null,'\t')}); 
		    	}
		    	$('#'+componentIdentifier).find('.js-component-timer').html(Date.now()-t0 + '&nbsp;ms');
		    });
			
		}
	};
	
	
	
	// Event handlers
	
	$('a[data-toggle="pill"]').on('shown.bs.tab',function(event){
		$(event.target.hash).find('.js-component-row').each(function( index ) {
			getServiceStatus(this.id);
		});
	});
	
	$('.js-component-edit').click(function(event){
		console.log("gOT u",$(event.target).closest('tr').attr('id'));
	})
	
});


	
