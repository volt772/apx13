package com.apx.linea.presentation.bottomsheet

//@Composable
fun SampleBottomSheet(
) {

}

/* 예시*/
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CategoryFilterBottomSheet(
//    showAll: Boolean = true,
//    categories: List<CategoryModel>,
//    selectedCategoryId: Long?,
//    onCategoryClick: (CategoryModel?) -> Unit,
//    onDismiss: () -> Unit
//) {
//    ModalBottomSheet(
//        onDismissRequest = onDismiss,
//        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//
//            // 전체 보기 옵션
//            if (showAll) {
//                Text(
//                    text = "전체",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { onCategoryClick(null) }
//                        .padding(12.dp),
//                    style = MaterialTheme.typography.bodyLarge
//                )
//
//                Divider(
//                    color = Color.LightGray,
//                    thickness = 1.dp,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp)
//                )
//            }
//
//            categories.forEach { category ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { onCategoryClick(category) }
//                        .padding(12.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = category.name,
//                        modifier = Modifier.weight(1f),
//                        style = MaterialTheme.typography.bodyLarge
//                    )
//
//                    if (selectedCategoryId == category.id) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_check),
//                            contentDescription = "",
//                            modifier = Modifier.size(24.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
