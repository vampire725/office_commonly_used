# 数据架构设计

- [说明](#说明)
- [词汇表清单](#词汇表清单)
- [Vertex-Edge关系图](#Vertex-Edge关系图)
- [顶点相关](#顶点相关)
    - [顶点类型表](#顶点类型表)
    - [顶点类别表](#顶点类别表)
    - [顶点属性表](#顶点属性表)
- [边相关](#边相关)
    - [边类型表](#边类型表)
    - [边类别表](#边类别表)
    - [边属性表](#边属性表)

## 说明

基于外交部的需求，我们从逻辑层面将顶点分为人`Human`，物`Item`，地`Location`，组织`Organization`，事`Event`五个类型(`Class`)，而每个类型又可划分为不同的概念`Concept`。

类似的，关系可依次划分为人-人，人-事，人-地...共计15个类型的关系，而每类关系又可分为组织关系`relation`和行为动作`action`两种类别。

但是具体到数据库，JanusGraph存储内容只有顶点`Vertex`和边`Edge`，以及他们具有的属性`properties`。因此，我们给出如下词汇表，规定每种分类下顶点/边/属性的具体内容，增删数据时，应该以最新词汇表为准。

## 词汇表清单

| 表名                 | 说明       |
| :------------------: | :--------: |
| Vertex-Class         | 顶点类型表 |
| Vertex-Type-Concept | 顶点类别表 |
| Vertex-Properties    | 顶点属性表 |
| Edge-Class           | 边类型表   |
| Edge-Type-Category   | 边类别表   |
| Edge-Properties      | 边属性表   |

<!-- 不知道说的是啥👇 -->
<!-- ## 1.1. Vertex_Edge
<!-- 不知道说的是啥👆 -->

## Vertex-Edge关系图

![Vertex_Edge关系图](./Data_design_files/Vertex_Edge.jpg)


## 顶点相关

### 顶点类型表

| Class_Id | Class_name | Class_english_name |
| :------: | :--------: | :----------------: |
| 1        | 人         | Human             |
| 2        | 物         | Item             |
| 3        | 地点       | Location           |
| 4        | 组织       | Organization       |
| 5        | 事件       | Event              |
| ...      | ...        | ...                |

### 顶点类别表

| Type_name                | Type_english_name                               | Category_name | Category_english_name         | Class_id |
| :----------------------: | :---------------------------------------------: | :-----------: | :---------------------------: | :------: |
| 恐怖分子                 | Dangerous person                                | 危险分子      | Dangerous molecule            | 1        |
| 杀手                     | Killer                                          | 危险分子      | Dangerous molecule            | 1        |
| 小偷                     | Thief                                           | 危险分子      | Dangerous molecule            | 1        |
| 医生                     | Doctors                                         | 人民群众      | Ordinary person               | 1        |
| 助理                     | Assistant Manager                               | 人民群众      | Ordinary person               | 1        |
| 律师                     | Lawyer                                          | 人民群众      | Ordinary person               | 1        |
| 司机                     | Driver                                          | 人民群众      | Ordinary person               | 1        |
| 记者                     | Reporter                                        | 人民群众      | Ordinary person               | 1        |
| 服务员                   | Waiter                                          | 人民群众      | Ordinary person               | 1        |
| 总统                     | President                                       | 政治军事      | Political military            | 1        |
| 特警                     | Special police                                  | 政治军事      | Political military            | 1        |
| 警察                     | Policemen                                       | 政治军事      | Political military            | 1        |
| 军人                     | Soldier                                         | 政治军事      | Political military            | 1        |
| 外交官                   | Diplomat                                        | 政治军事      | Political military            | 1        |
| 文件                     | file                                            | 普通物品      | Common items                  | 2        |
| 手机                     | Mobile phone                                    | 普通物品      | Common items                  | 2        |
| 货币                     | currency                                        | 普通物品      | Common items                  | 2        |
| 金银                     | Gold and silver                                 | 普通物品      | Common items                  | 2        |
| 汽车                     | car                                             | 普通物品      | Common items                  | 2        |
| 轮船                     | Ship                                            | 普通物品      | Common items                  | 2        |
| 飞机                     | aircraft                                        | 普通物品      | Common items                  | 2        |
| 军火                     | arms                                            | 危险物品      | Dangerous item                | 2        |
| 爆炸品                   | Explosives                                      | 危险物品      | Dangerous item                | 2        |
| 气体                     | gas                                             | 危险物品      | Dangerous item                | 2        |
| 易燃液体                 | flammable liquid                                | 危险物品      | Dangerous item                | 2        |
| 易燃固体                 | Flammable solid                                 | 危险物品      | Dangerous item                | 2        |
| 氧化性物质和有机过氧化物 | Oxidizing substances and organic peroxides      | 危险物品      | Dangerous item                | 2        |
| 毒性物质和感染性物质     | Toxic substances and infectious substances      | 危险物品      | Dangerous item                | 2        |
| 放射性物质               | Radioactive material                            | 危险物品      | Dangerous item                | 2        |
| 腐蚀性物质               | Corrosive substance                             | 危险物品      | Dangerous item                | 2        |
| 杂项危险物质和物品       | Miscellaneous dangerous substances and articles | 危险物品      | Dangerous item                | 2        |
| 地区                     | area                                            | 境外          | Overseas                      | 3        |
| 国家                     | country                                         | 境外          | Overseas                      | 3        |
| 省/州                    | province / State                                | 境外          | Overseas                      | 3        |
| 市                       | city                                            | 境外          | Overseas                      | 3        |
| 区                       | Area                                            | 境外          | Overseas                      | 3        |
| 县/郡                    | County/county                                   | 境外          | Overseas                      | 3        |
| 地标                     | landmark                                        | 境外          | Overseas                      | 3        |
| 地区                     | area                                            | 境内          | Domestic                      | 3        |
| 国家                     | country                                         | 境内          | Domestic                      | 3        |
| 省/州                    | province / State                                | 境内          | Domestic                      | 3        |
| 市                       | city                                            | 境内          | Domestic                      | 3        |
| 区                       | Area                                            | 境内          | Domestic                      | 3        |
| 县/郡                    | County/county                                   | 境内          | Domestic                      | 3        |
| 地标                     | landmark                                        | 境内          | Domestic                      | 3        |
| 官方组织                 | Official organization                           | 官方组织      | Official organization         | 4        |
| 宗教组织                 | Religious organization                          | 宗教组织      | Religious organization        | 4        |
| 民间组织                 | Civil society organization                      | 民间组织      | Civil society organization    | 4        |
| 恐怖组织                 | Terrorist organization                          | 恐怖组织      | Terrorist organization        | 4        |
| 军事组织                 | Military organization                           | 军事组织      | Military organization         | 4        |
| 非法军事组织             | Illegal military organization                   | 非法军事组织  | Illegal military organization | 4        |
| 节日                     | festival                                        | 普通事件      | Ordinary event                | 5        |
| 会议                     | meeting                                         | 普通事件      | Ordinary event                | 5        |
| 游行                     | procession                                      | 普通事件      | Ordinary event                | 5        |
| 集会                     | assembly                                        | 普通事件      | Ordinary event                | 5        |
| 地震                     | earthquake                                      | 自然灾害      | natural disaster              | 5        |
| 火山爆发                 | volcanic eruptions                              | 自然灾害      | natural disaster              | 5        |
| 泥石流                   | Debris flow                                     | 自然灾害      | natural disaster              | 5        |
| 海啸                     | Tsunami                                         | 自然灾害      | natural disaster              | 5        |
| 干旱                     | drought                                         | 自然灾害      | natural disaster              | 5        |
| 洪涝                     | Flood                                           | 自然灾害      | natural disaster              | 5        |
| 气象                     | meteorological                                  | 自然灾害      | natural disaster              | 5        |
| 战争                     | war                                             | 人为灾害      | Man-made disaster             | 5        |
| 枪击                     | Shooting                                        | 人为灾害      | Man-made disaster             | 5        |
| 暴乱                     | riot                                            | 人为灾害      | Man-made disaster             | 5        |
| 爆炸                     | explosion                                       | 人为灾害      | Man-made disaster             | 5        |
| 车祸                     | Car accident                                    | 人为灾害      | Man-made disaster             | 5        |
| 抢劫                     | robbery                                         | 人为灾害      | Man-made disaster             | 5        |
| 盗窃                     | theft                                           | 人为灾害      | Man-made disaster             | 5        |
| 强奸                     | rape                                            | 人为灾害      | Man-made disaster             | 5        |
| 杀人                     | kill                                            | 人为灾害      | Man-made disaster             | 5        |
| ...                      | ...                                             | ...           | ...                           | ...      |

### 顶点属性表

| 字段      | 名称                  | 数据类型 | 备注                   | 是否为空 |
| :-------: | :-------------------: | :------: | ---------------------- | -------- |
| Vertex_id | id                    | INT      | 顶点ID                 | 非空     |
| 名字      | Name                  | String   | 实体名字               | 非空     |
| 英文名字  | English_name          | String   | 实体英文名字           | 可空     |
| 别名      | Other_name            | String   | 其他名字               | 可空     |
| 缩写      | Abbreviation          | String   | 缩写                   | 可空     |
| 年龄      | Age                   | INT      | 年龄                   | 可空     |
| 性别      | Gender                | String   | 性别                   | 可空     |
| 出生日期  | Date_of_birth         | DATE     | 出生日期               | 可空     |
| 是否安全  | Is_dangerous          | INT      | 0代表不安全，1代表安全 | 可空     |
| 是否境外  | Is_overseas           | INT      | 0代表境内，1代表境外   | 可空     |
| 成立日期  | Date_of_establishment | DATE     | 成立日期               | 可空     |
| 描述      | Description           | String   | 事件内容描述           | 可空     |
| 类型      | Type_name             | String   | 所属类型               | 可空     |
| 类别      | Category              | String   | 所属类别               | 可空     |
| GEO       | Geography             | String   | 地理坐标: 经度，纬度   | 可空     |
| 创建时间  | Create_time           | DATETIME | 顶点创建时间           | 可空     |
| 更新时间  | Updata_time           | DATETIME | 顶点信息更新时间       | 可空     |
| ...       | ...                   | ...      | ...                    | ...      |

## 边相关

### 边类型表

| Class_id | Class_name | Class_english_name |
| :------: | :--------: | :----------------: |
| 1        | 关系       | Relationship       |
| 2        | 动作       | Action             |
| ...      | ...        | ...                |

### 边类别表

| Type_name | Type_English_name      | Category_name | Category_english_name     | Class_id |
| :-------: | :--------------------: | :-----------: | :-----------------------: | :------: |
| 父亲      | father                 | 人-人         | person-person             | 1        |
| 母亲      | mother                 | 人-人         | person-person             | 1        |
| 兄弟      | brothers               | 人-人         | person-person             | 1        |
| 姐妹      | sisters                | 人-人         | person-person             | 1        |
| 雇主      | employer               | 人-人         | person-person             | 1        |
| 恋人      | Lovers                 | 人-人         | person-person             | 1        |
| 亲戚      | relative               | 人-人         | person-person             | 1        |
| 恐吓      | Intimidate             | 人-人         | person-person             | 2        |
| 敲诈勒索  | Extortion              | 人-人         | person-person             | 2        |
| 强奸      | rape                   | 人-人         | person-person             | 2        |
| 伤害      | hurt                   | 人-人         | person-person             | 2        |
| 污蔑      | slander                | 人-人         | person-person             | 2        |
| 杀死      | Kill                   | 人-人         | person-person             | 2        |
| 控制      | control                | 人-人         | person-person             | 2        |
| 追踪      | track                  | 人-人         | person-person             | 2        |
| 拥有      | have                   | 人-物         | Person-object             | 1        |
| 使用      | use                    | 人-物         | Person-object             | 2        |
| 发现      | Find                   | 人-物         | Person-object             | 2        |
| 破坏      | damage                 | 人-物         | Person-object             | 2        |
| 获得      | obtain                 | 人-物         | Person-object             | 2        |
| 失去      | lose                   | 人-物         | Person-object             | 2        |
| 居住      | live                   | 人-地点       | Person-place              | 1        |
| 工作      | jobs                   | 人-地点       | Person-place              | 1        |
| 出生      | Born                   | 人-地点       | Person-place              | 1        |
| 到达      | Arrivals               | 人-地点       | Person-place              | 2        |
| 离开      | go away                | 人-地点       | Person-place              | 2        |
| 从属      | Subordinate            | 人-组织       | Person-organization       | 1        |
| 加入      | Join                   | 人-组织       | Person-organization       | 2        |
| 脱离      | Detach                 | 人-组织       | Person-organization       | 2        |
| 组织      | organization           | 人-事件       | Person-event              | 2        |
| 参与      | participate            | 人-事件       | Person-event              | 2        |
| 受害      | Victim                 | 人-事件       | Person-event              | 2        |
| 目击      | Witness                | 人-事件       | Person-event              | 2        |
| 依附      | Attach                 | 物-物         | Object-object             | 1        |
| 放置      | Place                  | 物-地点       | Object-location           | 1        |
| 属于      | belong                 | 物-组织       | Object-organization       | 1        |
| 产出      | output                 | 地点-物       | Location-object           | 2        |
| 相邻      | Adjacent               | 地点-地点     | Location-location         | 1        |
| 从属      | Subordinate            | 地点-地点     | Location-location         | 1        |
| 存在      | presence               | 地点-组织     | Location-organization     | 1        |
| 发生      | occur                  | 地点-事件     | Location-event            | 2        |
| 创始人    | Founder                | 组织-人       | Organizer-person          | 1        |
| 所有者    | owner                  | 组织-人       | Organizer-person          | 1        |
| 核心成员  | Core member            | 组织-人       | Organizer-person          | 1        |
| 中层人员  | Middle-level personnel | 组织-人       | Organizer-person          | 1        |
| 外围人员  | Peripheral personnel   | 组织-人       | Organizer-person          | 1        |
| 指派      | Assign                 | 组织-人       | Organizer-person          | 2        |
| 开除      | Expulsion              | 组织-人       | Organizer-person          | 2        |
| 控制      | control                | 组织-人       | Organizer-person          | 2        |
| 危害      | harm                   | 组织-人       | Organizer-person          | 2        |
| 使用      | use                    | 组织-物       | Organization-object       | 2        |
| 买入      | Buy                    | 组织-物       | Organization-object       | 2        |
| 租用      | Rent                   | 组织-物       | Organization-object       | 2        |
| 创造      | create                 | 组织-物       | Organization-object       | 2        |
| 失去      | lose                   | 组织-物       | Organization-object       | 2        |
| 驻扎      | Station                | 组织-地点     | Organization-location     | 1        |
| 发源      | Origin                 | 组织-地点     | Organization-location     | 1        |
| 搬迁      | move                   | 组织-地点     | Organization-location     | 2        |
| 突袭      | Assault                | 组织-地点     | Organization-location     | 2        |
| 从属      | Subordinate            | 组织-组织     | Organization-organization | 1        |
| 同盟      | alliance               | 组织-组织     | Organization-organization | 1        |
| 敌对      | hostility              | 组织-组织     | Organization-organization | 1        |
| 合作      | Cooperation            | 组织-组织     | Organization-organization | 2        |
| 对抗      | confrontation          | 组织-组织     | Organization-organization | 2        |
| 组织      | organization           | 组织-事件     | Organization-event        | 2        |
| 参与      | participate            | 组织-事件     | Organization-event        | 2        |
| 阻止      | prevent                | 组织-事件     | Organization-event        | 2        |
| 发生      | occur                  | 事件-地点     | Event-location            | 2        |
| 引发      | Trigger                | 事件-事件     | Event-event               | 1        |
| ...       | ...                    | ...           | ...                       | ...      |

### 边属性表

| 字段     | 名称          | 数据类型 | 备注           | 是否为空 |
| -------- | ------------- | -------- | :------------: | :------: |
| Edge_id  | id            | String   | 关系ID         | 非空     |
| A_id     | Vertex_id_a   | INT      | 顶点A_ID       | 非空     |
| B_id     | Vertex_id_b   | INT      | 顶点B_ID       | 非空     |
| 名字     | Name          | String   | 事件名字       | 非空     |
| 英文名字 | Englishi_name | String   | 英文名字       | 可空     |
| 时间     | Time          | DATETIME | 发生时间       | 可空     |
| 理由     | Reason        | String   | 发生理由       | 可空     |
| 描述     | Description   | String   | 关系描述       | 可空     |
| 类型     | Type_name     | String   | 所属类型       | 可空     |
| 类别     | Category      | String   | 所属类别       | 可空     |
| 创建时间 | Create_time   | DATETIME | 边创建时间     | 可空     |
| 更新时间 | Updata_time   | DATETIME | 边信息更新时间 | 可空     |
| ...      | ...           | ...      | ...            | ...      |