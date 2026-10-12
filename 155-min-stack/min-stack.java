class MinStack {
    private int[] stack;
    private int[] minStack;
    private int size;
    private int minSize;

    public MinStack() {
        stack = new int[10];
        minStack = new int[10];
        size = 0;
        minSize = 0;
    }

    public void push(int value) {
        if (size == stack.length) {
            int[] newStack = new int[stack.length * 2];
            System.arraycopy(stack, 0, newStack, 0, size);
            stack = newStack;
        }

        stack[size++] = value;

        if (minSize == 0 || value <= minStack[minSize - 1]) {
            if (minSize == minStack.length) {
                int[] newMinStack = new int[minStack.length * 2];
                System.arraycopy(minStack, 0, newMinStack, 0, minSize);
                minStack = newMinStack;
            }
            minStack[minSize++] = value;
        }
    }

    public void pop() {
        if (size == 0) {
            return;
        }

        int value = stack[--size];
        if (minSize > 0 && value == minStack[minSize - 1]) {
            minSize--;
        }
    }

    public int top() {
        if (size == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack[size - 1];
    }

    public int getMin() {
        if (minSize == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        return minStack[minSize - 1];
    }
}