(function (window) {
    window.harmfulTypes = [
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

                    ],
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

                    ],
                },
            ],
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
                            value: '2_1_1',
                        },
                    ],
                },
                {
                    label: '涉政信息',
                    value: '2_2',
                    children: [
                        {
                            label: '体制机制',
                            value: '2_2_1',
                        },
                        {
                            label: '发展历史',
                            value: '2_2_2',
                        },

                    ],
                },
                {
                    label: '涉军信息',
                    value: '2_3',
                    children: [
                        {
                            label: '军事机密',
                            value: '2_3_1',
                        },
                        {
                            label: '退伍军人',
                            value: '2_3_2',
                        },
                        {
                            label: '军队形象',
                            value: '2_3_3',
                        },
                    ],
                },
            ],
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
                    ],
                },
            ],
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
                            value: '4_1_1',
                        },
                        {
                            label: '民族政策',
                            value: '4_1_2',
                        },
                    ],
                },

                {
                    label: '非法宗教类',
                    value: '4_4',
                    children: [
                        {
                            label: '非法传教',
                            value: '4_4_1',
                        },
                        {
                            label: '未成年人信教',
                            value: '4_4_2',
                        },
                    ],
                },
            ],
        },
        {
            label: '治疆政策类',
            value: '5',
            children: [
                {
                    label: '民族团结一家亲',
                    value: '5_1',
                },
                {
                    label: '访慧聚',
                    value: '5_2',
                },
                {
                    label: '去极端化政策',
                    value: '5_3',
                },
            ],
        },
        {
            label: '公共安全类',
            value: '7',
            children: [
                {
                    label: '安全生产',
                    value: '7_1',
                },
                {
                    label: '网络安全',
                    value: '7_2',
                },

            ],
        },
        {
            label: '社会民生类',
            value: '9',
            children: [
                {
                    label: '卫生医疗类',
                    value: '9_1',
                },
                {
                    label: '工资福利类',
                    value: '9_2',
                },
                {
                    label: '教育事业类',
                    value: '9_3',
                },
            ],
        },
    ];
})(window);
