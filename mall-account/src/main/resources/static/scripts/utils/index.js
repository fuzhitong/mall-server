define(['utils/uLodash', 'utils/uMessageBox'], function(uLodash, messageBox){
  return {
    install (Clazz) {
      Clazz.use(uLodash)
      Clazz.use(messageBox)
    }
  }
})
