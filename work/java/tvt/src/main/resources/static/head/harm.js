var reportState  = {
    cascadeReportType: [
        {
            label: '涉领导人类',
            value: '1',
            children: [
                {
                    label: '涉中央领导',
                    value: '1_1',
                    children: [
                        {
                            label: '攻击领导',
                            value: '1_1_1',
                        },
                        {
                            label: '活动泄密',
                            value: '1_1_2',
                        },
                        {
                            label: '背景履历',
                            value: '1_1_3',
                        },
                        {
                            label: '社会关系',
                            value: '1_1_4',
                        },
                        {
                            label: '其他相关',
                            value: '1_1_5'
                        }
                    ]
                },
                {
                    label: '涉自治区领导',
                    value: '1_2',
                    children: [
                        {
                            label: '攻击领导',
                            value: '1_2_1',
                        },
                        {
                            label: '活动泄密',
                            value: '1_2_2',
                        },
                        {
                            label: '背景履历',
                            value: '1_2_3',
                        },
                        {
                            label: '社会关系',
                            value: '1_2_4',
                        },
                        {
                            label: '其他相关',
                            value: '1_2_5'
                        }
                    ]
                },
                {
                    label: '涉其他领导',
                    value: '1_3',
                    children: [
                        {
                            label: '涉现任中央领导',
                            value: '1_3_1'
                        },
                        {
                            label: '涉现任自治区领导',
                            value: '1_3_2'
                        },
                        {
                            label: '涉历任领导',
                            value: '1_3_3'
                        }
                    ]
                }
            ]
        },
        {
            label: '涉党政军类',
            value: '2',
            children: [
                {
                    label: '涉党信息',
                    value: '2_1',
                    children: [
                        {
                            label: '发展历史',
                            value: '2_1_1'
                        },
                        {
                            label: '理论思想',
                            value: '2_1_2'
                        },
                        {
                            label: '路线方针',
                            value: '2_1_3'
                        },
                        {
                            label: '各项制度',
                            value: '2_1_4'
                        }
                    ]
                },
                {
                    label: '涉政信息',
                    value: '2_2',
                    children: [
                        {
                            label: '体制机制',
                            value: '2_2_1'
                        },
                        {
                            label: '发展历史',
                            value: '2_2_2'
                        },
                        {
                            label: '政策措施',
                            value: '2_2_3'
                        },
                        {
                            label: '国家机密',
                            value: '2_2_4'
                        },
                        {
                            label: '形象荣誉',
                            value: '2_2_5'
                        }
                    ]
                },
                {
                    label: '涉军信息',
                    value: '2_3',
                    children: [
                        {
                            label: '军事机密',
                            value: '2_3_1'
                        },
                        {
                            label: '退伍军人',
                            value: '2_3_2'
                        },
                        {
                            label: '军队形象',
                            value: '2_3_3'
                        },
                        {
                            label: '军民关系',
                            value: '2_3_4'
                        },
                        {
                            label: '军队历史',
                            value: '2_3_5'
                        }
                    ]
                },
                {
                    label: '其他相关',
                    value: '2_4'
                }
            ]
        },
        {
            label: '涉爆涉恐类',
            value: '3',
            children: [
                {
                    label: '涉疆爆恐事件',
                    value: '3_1',
                    children: [
                        {
                            label: '旧事翻炒',
                            value: '3_1_1',
                        },
                        {
                            label: '违规发布',
                            value: '3_1_2',
                        },
                        {
                            label: '擅自发布',
                            value: '3_1_3',
                        }
                    ]
                },
                {
                    label: '境内爆恐信息',
                    value: '3_2',
                    children: [
                        {
                            label: '煽宣袭击',
                            value: '3_2_1',
                        },
                        {
                            label: '暴力恐怖',
                            value: '3_2_2',
                        },
                        {
                            label: '暴恐思想',
                            value: '3_2_3',
                        },
                        {
                            label: '教授技巧',
                            value: '3_2_4',
                        },
                        {
                            label: '鼓动迁徙',
                            value: '3_2_5'
                        },
                        {
                            label: '情报信息',
                            value: '3_2_6'
                        },
                        {
                            label: '重点人员',
                            value: '3_2_7'
                        },
                        {
                            label: '认可美化',
                            value: '3_2_8',
                        }
                    ]
                },
                {
                    label: '网上勾联渠道',
                    value: '3_3',
                    children: [
                        {
                            label: '即时通信工具',
                            value: '3_3_1'
                        },
                        {
                            label: 'VPN“翻墙”工具',
                            value: '3_3_2'
                        },
                        {
                            label: '可视网站',
                            value: '3_3_3'
                        }
                    ]
                },
                {
                    label: '境外暴恐动向',
                    value: '3_4'
                }
            ]
        },
        {
            label: '民族宗教类',
            value: '4',
            children: [
                {
                    label: '涉民族敏感信息类',
                    value: '4_1',
                    children: [
                        {
                            label: '攻击辱骂',
                            value: '4_1_1'
                        },
                        {
                            label: '民族政策',
                            value: '4_1_2'
                        },
                        {
                            label: '历史文化',
                            value: '4_1_3'
                        },
                        {
                            label: '风俗习惯',
                            value: '4_1_4'
                        },
                        {
                            label: '民族关系',
                            value: '4_1_5'
                        }
                    ]
                },
                {
                    label: '涉民族分裂信息类',
                    value: '4_2',
                    children: [
                        {
                            label: '“疆独”历史',
                            value: '4_2_1'
                        },
                        {
                            label: '分裂组织',
                            value: '4_2_2'
                        },
                        {
                            label: '分裂分子',
                            value: '4_2_3',
                        },
                        {
                            label: '反动标识',
                            value: '4_2_4'
                        },
                        {
                            label: '分裂言论',
                            value: '4_2_5'
                        },
                        {
                            label: '争议事件',
                            value: '4_2_6'
                        },
                        {
                            label: '思想渗透',
                            value: '4_2_7',
                        },
                        {
                            label: '境外敌对势力干预',
                            value: '4_2_8'
                        }
                    ]
                },
                {
                    label: '涉宗教敏感信息类',
                    value: '4_3',
                    children: [
                        {
                            label: '宗教政策',
                            value: '4_3_1'
                        },
                        {
                            label: '宗教活动',
                            value: '4_3_2'
                        },
                        {
                            label: '宗教教义',
                            value: '4_3_3'
                        },
                        {
                            label: '宗教人士',
                            value: '4_3_4'
                        },
                        {
                            label: '宗教场所',
                            value: '4_3_5'
                        },
                        {
                            label: '清真“泛化”',
                            value: '4_3_6'
                        }
                    ]
                },
                {
                    label: '非法宗教类',
                    value: '4_4',
                    children: [
                        {
                            label: '非法传教',
                            value: '4_4_1'
                        },
                        {
                            label: '未成年人信教',
                            value: '4_4_2'
                        },
                        {
                            label: '非法传播宗教出版物',
                            value: '4_4_3'
                        },
                        {
                            label: '非法传播宗教用品',
                            value: '4_4_4'
                        },
                        {
                            label: '宣扬邪教',
                            value: '4_4_5'
                        }
                    ]
                },
                {
                    label: '宗教极端信息类',
                    value: '4_5',
                    children: [
                        {
                            label: '原教旨主义',
                            value: '4_5_1'
                        },
                        {
                            label: '“圣战”迁徙论',
                            value: '4_5_2'
                        },
                        {
                            label: '教大于法',
                            value: '4_5_3'
                        },
                        {
                            label: '“火狱论”“地狱说”',
                            value: '4_5_4'
                        },
                        {
                            label: '宗教狂热',
                            value: '4_5_5'
                        }
                    ]
                }
            ]
        },
        {
            label: '治疆政策类',
            value: '5',
            children: [
                {
                    label: '民族团结一家亲',
                    value: '5_1'
                },
                {
                    label: '访慧聚',
                    value: '5_2'
                },
                {
                    label: '去极端化政策',
                    value: '5_3'
                },
                {
                    label: '严打行动',
                    value: '5_4'
                },
                {
                    label: '社会面防控',
                    value: '5_5',
                },
                {
                    label: '双语教育',
                    value: '5_6'
                },
                {
                    label: '转移就业',
                    value: '5_7'
                },
                {
                    label: '对口援疆政策',
                    value: '5_8'
                },
                {
                    label: '其他相关',
                    value: '5_9'
                }
            ]
        },
        {
            label: '涉疆案(事)件类',
            value: '6',
            children: [
                {
                    label: '涉疆案件',
                    value: '6_1',
                    children: [
                        {
                            label: '重大刑事案件',
                            value: '6_1_1'
                        },
                        {
                            label: '民事纠纷案件',
                            value: '6_1_2'
                        },
                        {
                            label: '涉疆经济案件',
                            value: '6_1_3'
                        },
                        {
                            label: '疆外涉疆案件',
                            value: '6_1_4'
                        },
                        {
                            label: '其他涉疆案件',
                            value: '6_1_5'
                        }
                    ]
                },
                {
                    label: '涉疆事件',
                    value: '6_2',
                    children: [
                        {
                            label: '群体性事件',
                            value: '6_2_1'
                        },
                        {
                            label: '社会纠纷事件',
                            value: '6_2_2'
                        },
                        {
                            label: '翻炒旧闻',
                            value: '6_2_3'
                        },
                        {
                            label: '执法冲突',
                            value: '6_2_4'
                        },
                        {
                            label: '地域歧视',
                            value: '6_2_5'
                        },
                        {
                            label: '其他敏感事件',
                            value: '6_2_6'
                        }
                    ]
                },
                {
                    label: '其他相关',
                    value: '6_3'
                }
            ]
        },
        {
            label: '公共安全类',
            value: '7',
            children: [
                {
                    label: '安全生产',
                    value: '7_1'
                },
                {
                    label: '网络安全',
                    value: '7_2'
                },
                {
                    label: '信息安全',
                    value: '7_3'
                },
                {
                    label: '灾害类',
                    value: '7_4',
                },
                {
                    label: '交通安全',
                    value: '7_5'
                },
                {
                    label: '食品安全',
                    value: '7_6'
                },
                {
                    label: '其他',
                    value: '7_7'
                }
            ]
        },
        {
            label: '组织人事类',
            value: '8',
            children: [
                {
                    label: '组织机构',
                    value: '8_1',
                },
                {
                    label: '领导职务调整',
                    value: '8_2',
                },
                {
                    label: '官员违规违纪违法类',
                    value: '8_3'
                },
                {
                    label: '检举揭发类',
                    value: '8_4',
                }
            ]
        },
        {
            label: '社会民生类',
            value: '9',
            children: [
                {
                    label: '卫生医疗类',
                    value: '9_1'
                },
                {
                    label: '工资福利类',
                    value: '9_2'
                },
                {
                    label: '教育事业类',
                    value: '9_3'
                },
                {
                    label: '社会保障类',
                    value: '9_4'
                },
                {
                    label: '劳动就业类',
                    value: '9_5'
                },
                {
                    label: '住房问题类',
                    value: '9_6'
                },
                {
                    label: '旅游事业类',
                    value: '9_7'
                },
                {
                    label: '生态环境类',
                    value: '9_8'
                },
                {
                    label: '其他民意反应类',
                    value: '9_9'
                }
            ]
        },
        {
            label: '其他类',
            value: '10',
            children: [
                {
                    label: '涉警有害信息类',
                    value: '10_1'
                },
                {
                    label: '淫秽色情类',
                    value: '10_2'
                },
                {
                    label: '赌博诈骗类',
                    value: '10_3'
                },
                {
                    label: '低俗媚俗类',
                    value: '10_4'
                },
                {
                    label: '暴力血腥类',
                    value: '10_5'
                },
                {
                    label: '教唆犯罪类',
                    value: '10_6'
                },
                {
                    label: '新闻报道类',
                    value: '10_7'
                },
                {
                    label: '侵犯个人权益类',
                    value: '10_8'
                },
                {
                    label: '其他',
                    value: '10_9'
                }
            ]
        }
    ]
};
