from collections import defaultdict

# Function to calculate the support count of itemsets
def calculate_support_count(itemsets, transactions):
    support_count = defaultdict(int)
    for transaction in transactions:
        for itemset in itemsets:
            if set(itemset).issubset(transaction):  # Convert itemset to set for checking
                support_count[itemset] += 1
    return support_count

# Pruning step: prune itemsets that have support below the minimum threshold
def prune(itemsets, support_count, min_support):
    pruned_itemsets = set()
    for itemset, count in support_count.items():
        if count >= min_support:
            pruned_itemsets.add(itemset)
    return pruned_itemsets

# Function to generate candidate itemsets of size k from frequent itemsets of size k-1
def generate_candidates(frequent_itemsets, k):
    candidates = set()
    frequent_itemsets_list = list(frequent_itemsets)
    
    for i in range(len(frequent_itemsets_list)):
        for j in range(i + 1, len(frequent_itemsets_list)):
            itemset1, itemset2 = frequent_itemsets_list[i], frequent_itemsets_list[j]
            # Generate a new candidate by merging itemsets of size k-1
            if len(set(itemset1).union(itemset2)) == k:
                candidates.add(tuple(sorted(set(itemset1).union(itemset2))))  # Use tuple for immutability
    
    return candidates

# Apriori algorithm implementation
def apriori(transactions, min_support):
    # Step 1: Generate 1-itemsets (candidate itemsets of size 1)
    items = set()
    for transaction in transactions:
        for item in transaction:
            items.add((item,))  # Use tuple (item,) to make it immutable
    
    current_itemsets = items
    frequent_itemsets = set()
    
    # Step 2: Generate frequent itemsets
    while current_itemsets:
        # Calculate support counts for current itemsets
        support_count = calculate_support_count(current_itemsets, transactions)
        
        # Prune the itemsets based on minimum support
        frequent_candidates = prune(current_itemsets, support_count, min_support)
        
        # If no frequent itemsets, break the loop
        if not frequent_candidates:
            break
        
        # Add the frequent itemsets to the final list
        frequent_itemsets.update(frequent_candidates)
        
        # Step 3: Generate candidate itemsets for the next iteration (size k + 1)
        current_itemsets = generate_candidates(frequent_candidates, len(next(iter(frequent_candidates))) + 1)
    
    return frequent_itemsets


# Example usage:
transactions = [
    {'A','B','E'},
    {'B', 'D'},
    {'B', 'C'},
    {'A', 'B','D'},
    {'A', 'C'},
    {'B','C'},
    {'A', 'C'},
    {'A', 'B','C','E'},
    {'A','B','C'}
]

min_support = 2

# Running the Apriori algorithm
frequent_itemsets = apriori(transactions, min_support)

# Displaying only the frequent itemsets
print("Frequent Itemsets:")
for itemset in frequent_itemsets:
    print(itemset)
