<template>
  <div class="notification-container">
    <div v-for="notification in notifications" :key="notification.id" :class="{
      'notification': true,
      [notification.type]: true,
      'show': notification.show
    }">
      <span class="notification-message">{{ notification.message }}</span>
      <button @click="removeNotification(notification.id)" class="notification-close-button">&times;</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const notifications = ref([]);
let notificationId = 0;

const addNotification = (message, type = "error", duration = 5000) => {
  const id = notificationId++;
  const newNotification = {
    id,
    message,
    type, // 'error', 'success', 'info', 'warning'
    show: false,
  };
  notifications.value.push(newNotification);

  requestAnimationFrame(() => {
    const index = notifications.value.findIndex(n => n.id === id);
    if (index !== -1) {
      notifications.value[index].show = true;
    }
  });

  if (duration) {
    setTimeout(() => {
      removeNotification(id);
    }, duration);
  }
};

const removeNotification = (id) => {
  const index = notifications.value.findIndex(n => n.id === id);
  if (index !== -1) {
    notifications.value[index].show = false;
    setTimeout(() => {
      notifications.value = notifications.value.filter(n => n.id !== id);
    }, 300);
  }
};

defineExpose({
  addNotification
});

</script>
