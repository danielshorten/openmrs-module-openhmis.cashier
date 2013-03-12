openhmis.testData = {};
openhmis.testData.JSON = {
	itemCollection: '\
		{"results":[{\
			"uuid":"20aa2858-6642-4b7a-b456-b07621c26538",\
			"name":"Ciprofloxacin",\
			"codes":[\
				{\
					"uuid":"f546f45d-b000-4f9f-be93-1ec29aea0a8b",\
					"description":null,\
					"retired":false,\
					"retireReason":null,\
					"code":"P3",\
					"resourceVersion":"1.8"\
				},\
				{\
					"uuid":"4a37da9f-7e9d-4200-b518-8e1336ebaa96",\
					"description":null,\
					"retired":false,\
					"retireReason":null,\
					"code":"P1",\
					"resourceVersion":"1.8"\
				}\
			],\
			"department":{\
				"uuid":"faf2f364-189c-4959-9428-4f917f52b8de",\
				"name":"Pharmacy"\
			}\
		}]}',
	
	item: '{\
		"uuid":"20aa2858-6642-4b7a-b456-b07621c26538",\
		"name":"Ciprofloxacin",\
		"description":null,\
		"retired":false,\
		"retireReason":null,\
		"codes":[\
			{\
				"uuid":"f546f45d-b000-4f9f-be93-1ec29aea0a8b",\
				"code":"P3"\
			},\
			{\
				"uuid":"4a37da9f-7e9d-4200-b518-8e1336ebaa96",\
				"code":"P1"\
			}\
		],\
		"prices":[\
			{\
				"uuid":"1b81faeb-8380-4400-92d8-176f9282f06c",\
				"name":"AIDS",\
				"retired":false,\
				"price":1000.00\
			},\
			{\
				"uuid":"4c99f971-7d44-4bfc-acff-ad5d395c2e39",\
				"name":"Default",\
				"retired":false,\
				"price":4.40\
			}\
		],\
		"department":{\
			"uuid":"faf2f364-189c-4959-9428-4f917f52b8de",\
			"name":"Pharmacy"\
		},\
		"defaultPrice":{\
			"uuid":"1b81faeb-8380-4400-92d8-176f9282f06c",\
			"price":1000.00\
		},\
		"resourceVersion":"1.8"\
	}',
	
	departmentCollection: '{\
		"results":[\
			{\
				"uuid":"faf2f364-189c-4959-9428-4f917f52b8de",\
				"name":"Pharmacy",\
				"retired":false\
			},\
			{\
				"uuid":"f53f0242-b9df-4b56-8734-4e1c31903f96",\
				"name":"Lab",\
				"retired":false\
			}\
		],\
		"length":2\
	}',
	
	bill: '{\
		"lineItems":\
		[{\
			"item":"368a0146-007c-4e1e-9dfb-fa04aac1d675",\
			"quantity":1,\
			"price":100,\
			"lineItemOrder":"0"\
		}],\
		"payments":[],\
		"status":"PENDING",\
		"patient":"e14250a5-4e90-4bbd-8fdf-e81fa50e1e53",\
		"cashPoint":"fae1359d-e7e0-4a1b-a3a5-5745789f8769"\
	}',
	
	payment: '{\
		"amount":100,\
		"amountTendered":100,\
		"paymentMode":"9b7c3730-b9e5-4e2b-afc4-82076b0e531c",\
		"attributes":[\
			{\
				"paymentModeAttributeType":"85f493d0-fdda-4d33-bbcc-f30d786f1668",\
				"value":"6101"\
			},\
			{\
				"paymentModeAttributeType":"5090715a-b44f-4324-ad05-51bc6382dfa9",\
				"value":"654"\
			}\
		]\
	}'
}