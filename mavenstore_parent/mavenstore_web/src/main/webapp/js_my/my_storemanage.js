	$(function() {
		
		//添加光标聚焦
		$("form input[name='nm']").focus();

		
		//添加取消操作,返回显示页面按钮
		$("#returnbutton").click(function(event){
			var isConfirm =window.confirm("您确认要取消操作吗?");
			if(!isConfirm){
				event.preventDefault();
			}else{
				window.location.href="${pageContext.request.contextPath}/goodsAction_listAllGoods.action";
			}
	});
		
		
		//查询显示下拉链表选择框AJAX实现
		$.get("${pageContext.request.contextPath}/storeAction_ajaxList.action",function(data){
			$(data).each(function(){
				var $option=$("<option value='"+this.id+"'>"+this.name+"</option>");
				$("#selectId").append($option);
			});
	});
		
		//添加简记码离焦事件
		$("input[name='nm']").blur(function(){
			$.post("${pageContext.request.contextPath}/goodsAction_findGoodsByNm.action",{"nm":$(this).val()},function(data){
				if(data!=null){
					//查询到商品,可以回显
					$("input[name='name']").val(data.name);
					$("input[name='unit']").val(data.unit);
					//这是隐藏域中的商品id信息
					$("#goodsId").val(data.id);
					//回显其对应的仓库信息
					$("#selectId").val(data.store.id);// 通过val函数使select选中
					
					//alert($("#selectId").val());
					//alert(data.store.id);
					
					//回显的时候,将不需要修改的属性disabled;
					$("input[name='unit']").attr("disabled","true");
					
					//数量默认为0
					$("input[name='amount']").val("");
					
					//提交按钮禁用
					$("#submitbutton").attr("disabled",false);
					
				}else{
					//没有该商品,清空显示数据即可;
					$("input[name='name']").val("");
					$("input[name='unit']").val("");
					$("#goodsId").val("");
					$("#selectId").val("0");// 通过val函数t选中默认值
					
					//回显的时候,将不需要修改的属性disabled;
					$("input[name='unit']").attr("disabled","false");
					
					//数量默认为0
					$("input[name='amount']").val("");
					
					//提交按钮禁用
					$("#submitbutton").attr("disabled","disabled");
				}
				
			});
		});
		
		
		//自定义的全局变量,用于给下面的自动补全回显提供使用
		var tempdata;
	// 货物名称输入自动联想提示(data就是一个数组就行了,这里使用点击click事件就行了,以newi访问一次就可以了)
	//这里有个小问题,必须手动点击一下才可以不被清空(下面加一个判断条件)
	$("input[name='name']").focus(function(){
	$.get("${pageContext.request.contextPath}/goodsAction_listAllGoods_ajax.action",function(data){
		//这个数据是给下面使用的;	
		tempdata=data;
		var arr=new Array();
		var i=0;
		$(data).each(function(){
			arr[i++]=this.name;
		});
			//alert(arr);
		$("input[name='name']").autocomplete({
			//source: ["电冰箱","洗衣机","电热水器","电微波炉","电话"]
			source:arr
		});
	});
	});
		
	//添加商品信息自动补全,这时候不用再发送请求了;
	$("input[name='name']").blur(function(){
		//这里需要查询取出该对象的其他值,保存到这个数组中(这是一个多维数组);
		var data=tempdata;
		var storeid;
		//获取当前选择的名称;
		var namevalue=$("input[name='name']").val();
		//alert(namevalue);
		//遍历数组,获取需要的对象
		$(tempdata).each(function(){
			if(this.name==namevalue){
				data=this;
				//storeid=this.store.id;
				//使用这个语句可以结束each循环;
				return false;
				alert("没有返回");
			}
		});
		
		//查询到商品,可以回显(但需要判断是否选择了对应的数据)
		if(data!=null){
			if($("input[name='name']").val()!=null&&(!($("input[name='name']").val().isEmpty))){
				//alert(storeid);
				$("input[name='nm']").val(data.nm);
				$("input[name='unit']").val(data.unit);
				//这是隐藏域中的商品id信息
				$("#goodsId").val(data.id);
				//回显其对应的仓库信息
				$("#selectId").val(data.store.id);// 通过val函数使select选中
				//$("#selectId").val(storeid);// 通过val函数使select选中
				
				//回显的时候,将不需要修改的属性disabled;
				$("input[name='unit']").attr("disabled","true");
				
				//数量默认为0
				$("input[name='amount']").val("");
				
				//提交按钮开启
				$("#submitbutton").attr("disabled",false);
			}
		}else{
			//没有该商品,清空显示数据即可;
			$("input[name='nm']").val("");//这时候的简记码是需要手动输入的;
			$("input[name='unit']").val("");
			$("#goodsId").val("");
			$("#selectId").val("0");// 通过val函数t选中默认值
			
			//回显的时候,将不需要修改的属性disabled;
			$("input[name='unit']").attr("disabled","true");
			
			//数量默认为0
			$("input[name='amount']").val("");
			
			//提交按钮禁用
			$("#submitbutton").attr("disabled",true);
		}
	});	
		
		
	});