import numpy as np
import random


# Function to compute the Euclidean distance between two points
def euclidean_distance(point1, point2):
    return np.sqrt(np.sum((point1 - point2) ** 2))


# K-means algorithm
def kmeans(X, K, max_iters=100):
    # Step 1: Randomly initialize centroids
    centroids = X[random.sample(range(X.shape[0]), K)]

    # To store the cluster assignments for each point
    clusters = np.zeros(X.shape[0])

    for i in range(max_iters):
        # Step 2: Assign points to the nearest centroid
        for j in range(X.shape[0]):
            distances = np.array([euclidean_distance(X[j], centroid) for centroid in centroids])
            clusters[j] = np.argmin(distances)

        # Step 3: Update centroids (mean of the points in each cluster)
        new_centroids = np.array([X[clusters == k].mean(axis=0) for k in range(K)])

        # If centroids do not change, we have converged
        if np.all(centroids == new_centroids):
            break

        centroids = new_centroids

    return centroids, clusters


# Example usage with random data
if __name__ == "__main__":
    # Generate a random dataset with 2 features (for simplicity)
    np.random.seed(42)
    X = np.random.rand(100, 2)  # 100 points in 2D

    K = 3  # Number of clusters

    centroids, clusters = kmeans(X, K)

    print("Centroids:\n", centroids)
    print("Cluster assignments:\n", clusters)
