function  isPassStr(str) {
	if (str == null || str.length == 0 || str.length < 6 || str.length > 12) {
		return false;
	}
	var count1 = 0;
	var count2 = 0;
	for (var i = 0; i < str.length; i++) {
		var ch = str.charAt(i);
		if (ch >= '0' && ch <= '9')
			count1++;
		else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
			count2++;
		} else
			return false;
	}
	return (count1 > 0 && count2 > 0);
}