var LoginTimeOut = function() {
	this.itemName = "${loginTimeOut}";
	this.dbKey = "LOGIN_TIME_OUT";
};
lang.extend(LoginTimeOut, 'freequery.config.configitem.AbstractSystemConfigItem');

// 初始化返回一个tr
LoginTimeOut.prototype.init = function() {
	// 创建tr
	this.LoginTimeOutTr = document.createElement("tr");
	this.LoginTimeOutTr.height = 30;
	
	// 创建内容为：上传Excel文件存放位置的td
	this.LoginTimeOutNameTd = document.createElement("td");
	this.LoginTimeOutNameTd.align = "left";
	this.LoginTimeOutNameTd.width = "200px";
	this.LoginTimeOutNameTd.innerHTML = this.itemName + ": ";
	this.LoginTimeOutTr.appendChild(this.LoginTimeOutNameTd);

	// 创建文本框
	this.LoginTimeOutTd = document.createElement("td");
	this.LoginTimeOutTd.innerHTML = "<input type='text' name='loginTimeOut' id='loginTimeOut' class='_loginTimeOut' width='100%'/>";
	this.LoginTimeOutTr.appendChild(this.LoginTimeOutTd);
	
	// 创建提示td
	this.LoginTimeOutTipTd = document.createElement("td");
	this.LoginTimeOutTipTd.innerHTML = "${loginTimeOutTip}";
	this.LoginTimeOutTr.appendChild(this.LoginTimeOutTipTd);
	// 创建恢复默认值按钮
	this.recoveryBtnTd = document.createElement("td");
	this.recoveryBtnTd.innerHTML = "<input class='button-buttonbar-noimage _defBtn' value='${recoveryBtn}'"
			+ "type='button' style='width:100%;' />";
	this.LoginTimeOutTr.appendChild(this.recoveryBtnTd);
	// 找到恢复初始值的按钮
	this.recoveryBtn = domutils.findElementByClassName([ this.LoginTimeOutTr ], "_defBtn");
	// 找到文本框
	this.LoginTimeOutText = domutils.findElementByClassName([ this.LoginTimeOutTr ], "_loginTimeOut");
	// 添加事件
	this.addListener(this.recoveryBtn, "click", this.recoveryValue, this);
	return this.LoginTimeOutTr;
};

// 对于从系统配置表里的获取数据的配置项来说，需要在初始化后根据配置信息来显示
LoginTimeOut.prototype.handleConfig = function(systemConfig) {
	for (var i in systemConfig) {
		var config = systemConfig[i];
		if (config && config.key == this.dbKey) {
			var recovery = config.value;
			this.LoginTimeOutText.value = recovery;
			break;
		}
	}
};

// 保存
LoginTimeOut.prototype.save = function() {
	if (!this.validate()) {
		return false;
	}
	var timeOut = this.LoginTimeOutText.value;
	var obj = {
			key : this.dbKey,
			value : '' + timeOut
	};
	alert("${settingTimeOutSuccess}" + timeOut + "s");
	return obj;
};

// 恢复为初始化值
LoginTimeOut.prototype.recoveryValue = function() {
	if (!this.validate()) {
		return false;
	}
	var timeOut = "30";
	var obj = {
			key : this.dbKey,
			value : '' + timeOut
	};
	this.LoginTimeOutText.value = timeOut;
	alert("${recoveryValue}" + timeOut);
	return obj;
};

// 检查配置信息是否合法
LoginTimeOut.prototype.validate = function() {
	var timeOut = this.LoginTimeOutText.value;
	var reg = /^[1-9]\d*$/;
	if (reg.test(timeOut)) {
		return true;
	}
	alert("${inputTimeOutReg}");
	return false;
};