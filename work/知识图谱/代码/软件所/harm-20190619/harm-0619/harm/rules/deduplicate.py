import sys
sys.path.append('..')

def deduplication(path, out_path):
	rule_seen = set()
	with open(path, 'r') as inf:
		with open(out_path, 'w') as outf:
			for line in inf:
				if not line:
					continue
				if line not in rule_seen:
					rule_seen.add(line)
					outf.write(line)

if __name__ == '__main__':
	deduplication('rules.txt', 'derules.txt')