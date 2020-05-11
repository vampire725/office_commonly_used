def fmt(item):
    save_data = {}
    if item['triple']:
        save_data['relation'] = item['triple']
    if item['entity']:
        save_data['entity'] = item['entity']
    return save_data