# v1.1.4 20190827
- 修复不符合级联编码规则的特殊数据，能够正产返回所有级联数据
- 增加父编码映射配置和映射功能，能够正确返回不符合级联编码规则的级联数据

# v1.1.3 20190718
- 修复编码规则匹配异常，能够正确返回所有符合级联编码规则的数据

# v1.1.2 20180827
- 修复bug，正常返回level字段
- 框架接口统一使用resteasy json provider
- 城市拼音统一移除市，自治州，地区

# v1.1.1 20180824
- 修复城市拼音多音字问题
- 增加数据版本接口
- 增加特殊区域开关，是否返回特殊行政区域（香港、澳门、台湾、钓鱼岛），默认关闭

# v1.0.0 
- 初始版本