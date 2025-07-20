class Solution {
     static class Node {
        String name;
        Map<String, Node> children = new HashMap<>();
        String serial; // for subtree serialization
        boolean deleted = false;

        Node(String name) {
            this.name = name;
        }
    }

    // Root of the tree
    private Node root = new Node("");

    public List<List<String>> deleteDuplicateFolder(List<List<String>> paths) {
         // 1. Build folder tree
        for (List<String> path : paths) {
            Node cur = root;
            for (String folder : path) {
                cur.children.putIfAbsent(folder, new Node(folder));
                cur = cur.children.get(folder);
            }
        }

        // 2. Serialize and find duplicates
        Map<String, List<Node>> map = new HashMap<>();
        serialize(root, map);

        // 3. Mark duplicates for deletion
        for (List<Node> list : map.values()) {
            if (list.size() > 1) {
                for (Node node : list) {
                    node.deleted = true;
                }
            }
        }

        // 4. Reconstruct valid paths
        List<List<String>> result = new ArrayList<>();
        dfs(root, new ArrayList<>(), result);
        return result;
    }

    // DFS to serialize subtrees
    private String serialize(Node node, Map<String, List<Node>> map) {
        if (node.children.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        List<String> childSerials = new ArrayList<>();

        for (Node child : node.children.values()) {
            String serial = serialize(child, map);
            childSerials.add("(" + child.name + serial + ")");
        }

        Collections.sort(childSerials); // order-independent
        for (String s : childSerials) sb.append(s);

        node.serial = sb.toString();
        map.computeIfAbsent(node.serial, k -> new ArrayList<>()).add(node);
        return node.serial;
    }

    // DFS to collect all remaining paths
    private void dfs(Node node, List<String> path, List<List<String>> result) {
        for (Node child : node.children.values()) {
            if (!child.deleted) {
                path.add(child.name);
                result.add(new ArrayList<>(path));
                dfs(child, path, result);
                path.remove(path.size() - 1);
            }
        }
    }
    }
