import itertools
from collections import defaultdict


# Step 1: Define the function to get the support count of each itemset
def get_support_count(transactions, itemsets):
    itemset_counts = defaultdict(int)
    for transaction in transactions:
        transaction_set = set(transaction)
        for itemset in itemsets:
            if itemset.issubset(transaction_set):
                itemset_counts[itemset] += 1
    return itemset_counts


# Step 2: Generate candidate itemsets of size 1 (C1)
def generate_C1(transactions):
    itemsets = defaultdict(int)
    for transaction in transactions:
        for item in transaction:
            itemsets[frozenset([item])] += 1
    return itemsets


# Step 3: Generate candidate itemsets of size k (Ck) by joining (Lk-1) with itself
def generate_Ck(Lk_1, k):
    candidates = set()
    itemsets_list = list(Lk_1.keys())
    for i in range(len(itemsets_list)):
        for j in range(i + 1, len(itemsets_list)):
            itemset1 = itemsets_list[i]
            itemset2 = itemsets_list[j]
            # Check if the first k-2 items are the same
            if len(itemset1.intersection(itemset2)) == k - 2:
                candidates.add(itemset1.union(itemset2))
    return candidates


# Step 4: Prune candidate itemsets based on the support of their subsets
def prune_Ck(Ck, Lk_1):
    pruned_candidates = set()
    for candidate in Ck:
        # Check if all subsets of candidate are frequent
        subsets = list(
            itertools.chain.from_iterable(itertools.combinations(candidate, r) for r in range(1, len(candidate))))
        subsets = [frozenset(subset) for subset in subsets]
        if all(subset in Lk_1 for subset in subsets):
            pruned_candidates.add(candidate)
    return pruned_candidates


# Step 5: Apply the Apriori algorithm
def apriori(transactions, min_support_count):
    # Step 5.1: Find frequent itemsets of size 1 (L1)
    C1 = generate_C1(transactions)
    L1 = {itemset: count for itemset, count in C1.items() if count >= min_support_count}

    frequent_itemsets = dict(L1)
    k = 2

    while True:
        # Step 5.2: Generate candidate itemsets of size k (Ck)
        Ck = generate_Ck(frequent_itemsets, k)
        # Step 5.3: Get the support count for the candidate itemsets
        Ck_support = get_support_count(transactions, Ck)
        # Step 5.4: Prune Ck based on the minimum support count
        Lk = {itemset: count for itemset, count in Ck_support.items() if count >= min_support_count}

        if not Lk:  # If no frequent itemsets found, stop
            break

        frequent_itemsets.update(Lk)
        k += 1

    return frequent_itemsets


# Step 6: Generate association rules from frequent itemsets
def generate_association_rules(frequent_itemsets, min_confidence):
    rules = []
    for itemset, support_count in frequent_itemsets.items():
        subsets = list(
            itertools.chain.from_iterable(itertools.combinations(itemset, r) for r in range(1, len(itemset))))
        subsets = [frozenset(subset) for subset in subsets]

        for subset in subsets:
            remaining = itemset - subset
            if remaining:
                # Calculate confidence
                support_subset = frequent_itemsets.get(subset, 0)
                confidence = support_count / support_subset if support_subset else 0

                # If confidence is greater than or equal to the minimum confidence
                if confidence >= min_confidence:
                    rules.append((tuple(subset), tuple(remaining), confidence))

    return rules


# Step 7: Print the association rules
def print_association_rules(rules):
    print("Association Rules (with minimum confidence):")
    for rule in rules:
        print(f"{set(rule[0])} → {set(rule[1])} (Confidence: {rule[2]:.2f})")


# Step 8: Accept user inputs for transactions, minimum support, and minimum confidence
def get_user_input():
    # Accepting transactions as user input
    num_transactions = int(input("Enter the number of transactions: "))
    transactions = []
    print("Enter each transaction (space-separated items):")
    for _ in range(num_transactions):
        transaction = input().split()
        transactions.append(transaction)

    # Accepting minimum support count and minimum confidence
    min_support_count = int(input("Enter the minimum support count: "))
    min_confidence = float(input("Enter the minimum confidence (as a decimal, e.g. 0.75 for 75%): "))

    return transactions, min_support_count, min_confidence


# Step 9: Run the Apriori algorithm and generate association rules
transactions, min_support_count, min_confidence = get_user_input()

# Find frequent itemsets
frequent_itemsets = apriori(transactions, min_support_count)

# Generate association rules
association_rules = generate_association_rules(frequent_itemsets, min_confidence)

# Print frequent itemsets and association rules
print("Frequent Itemsets:")
for itemset, support in frequent_itemsets.items():
    print(f"{set(itemset)} : {support}")

print_association_rules(association_rules)

