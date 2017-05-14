/**
 * 
 */
$(document).ready(function() {
	
	var checkNotEmpty = function(element){
		if(!element.val()){
			element.closest('.form-group').addClass('has-error has-feedback');
			return false;
		}else{
			element.closest('.form-group').removeClass('has-error has-feedback');
			return true;
		}
	};
	
	var getOperations = function(selectedOperation) {
		if(checkNotEmpty($('#bindings'))){
			$('#operations').find('option').remove();
			
			$.ajax({
		        url: "/soapFiddle/getOperations",
		        dataType: "json",
		        data: {bindingName: $('#bindings').val()}
		    }).then(function(operations) {
		    	if(operations){
		    		operations.forEach(function(operation){
		    		if(operation && operation.operationName){
			    		   $('#operations').append('<option value="'+operation.operationName+'" '+((selectedOperation&&selectedOperation==operation.operationName?'selected':''))+'>'+operation.operationName+'</option>');
			    	}
		    		});
		    	}
		    });
		}
	};
	
	var postDataToUrl = function() {
		// validate
		if(checkNotEmpty($('#bindings')) && checkNotEmpty($('#operations')) && checkNotEmpty($('#urlForPost')) && checkNotEmpty($('#soapRequest'))){
			$('#soapResponse').val('Requesting...');
			$.ajax({
				method: "POST",
		        url: "/soapFiddle/postDataToUrl",
		        dataType: "json",
		        contentType: 'application/json',
		        data: JSON.stringify({bindingName: $('#bindings').val(), operationName: $('#operations').val(), urlForPost:$('#urlForPost').val(), soapRequest: $('#soapRequest').val()})
		    }).then(function(response) {
		    	$('#soapResponse').val(response.soapResponse);
		    });
		}
	};

	var generateSampleRequest = function() {
		// validate
		if(checkNotEmpty($('#bindings')) && checkNotEmpty($('#operations'))){
			$('#soapRequest').val('Requesting...');
			$.ajax({
				method: "POST",
		        url: "/soapFiddle/generateSampleRequest",
		        dataType: "json",
		        contentType: 'application/json',
		        data: JSON.stringify({bindingName: $('#bindings').val(), operationName: $('#operations').val()})
		    }).then(function(response) {
		    	$('#soapRequest').val(response.soapRequest);
		    });
		}
	};
	// Add listeners
	$('document')
	$('#bindings').change(getOperations);
	$('#postDataToUrl').click(postDataToUrl);
	$('#generateSampleRequest').click(generateSampleRequest)
	
	$( document ).ajaxError(function( event, jqxhr, settings, thrownError ) {
		$('#soapResponse').val('');
		$('#errorMessageText').html(jqxhr.responseText);
		$('#errorMessage').removeClass('hidden');
		$('#errorMessage').get(0).scrollIntoView();
	});
	
	$( document ).ajaxStart(function(){
		$('#errorMessage').addClass('hidden');
	}); 
	
	if($('#bindings').val()){
		getOperations($('#operations').attr('data-selectedValue'));
	}
});