class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
         int p1 = m - 1;         // pointer for nums1
        int p2 = n - 1;         // pointer for nums2
        int p = m + n - 1;      // pointer for placement in nums1

        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] > nums2[p2]) {
                nums1[p] = nums1[p1];
                p1--;
            } else {
                nums1[p] = nums2[p2];
                p2--;
            }
            p--;
        }

        // Only need to add remaining nums2 elements if any
        while (p2 >= 0) {
            nums1[p] = nums2[p2];
            p--;
            p2--;
        }
    }
    }
