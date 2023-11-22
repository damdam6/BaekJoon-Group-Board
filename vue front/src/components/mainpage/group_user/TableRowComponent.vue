<!-- TableRowComponent.vue -->
<template>
    <tr>
        <td>{{ metricName }}</td>
        <td>{{ userData1 }}</td>
        <td>{{ userData2 }}</td>
    </tr>
</template>

<script>
export default {
    props: {
        metricName: String,
        userData1: [String, Number],
        userData2: [String, Number]
    }
}
</script>
