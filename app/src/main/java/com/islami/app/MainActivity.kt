package com.islami.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*

data class Section(val title:String,val subtitle:String,val route:String,val icon:androidx.compose.ui.graphics.vector.ImageVector)

val sections=listOf(
 Section("القرآن الكريم","قراءة، تفسير، بحث، ورد، ختمة، علامات","quran",Icons.Default.MenuBook),
 Section("الأذكار والأدعية","تصنيفات، عداد، ورد، مفضلة، بحث","adhkar",Icons.Default.Favorite),
 Section("الأحاديث","الكتب، الأبواب، البحث، المصدر والدرجة","hadith",Icons.Default.LibraryBooks),
 Section("قصص الأنبياء","25 قصة، أحداث، آيات، دروس، مفضلة","stories",Icons.Default.AutoStories),
 Section("التسبيح","عداد، أهداف، جلسات، سجل","tasbeeh",Icons.Default.TouchApp),
 Section("السيرة النبوية","خط زمني، أحداث، بحث، مفضلة","seerah",Icons.Default.HistoryEdu),
 Section("أسماء الله الحسنى","المعاني، الأدلة، مفضلة","names",Icons.Default.Spa),
 Section("الفقه","الطهارة، الصلاة، الصيام، الزكاة، الحج","fiqh",Icons.Default.School),
 Section("رمضان","خطة، ختمة، ورد، متابعة الإنجاز","ramadan",Icons.Default.Nightlight),
 Section("الحج والعمرة","دليل المناسك، الأدعية، قائمة الإنجاز","hajj",Icons.Default.Place),
 Section("الزكاة","حاسبات، نصاب، شرح ومصادر","zakat",Icons.Default.Calculate),
 Section("التعلم الإسلامي","دروس، اختبارات، مستويات، مراجعة","learning",Icons.Default.Psychology)
)

class MainActivity:ComponentActivity(){
 override fun onCreate(state:Bundle?){super.onCreate(state);setContent{IslamiApp()}}
}

@Composable
fun IslamiApp(){
 val nav=rememberNavController()
 NavHost(navController=nav,startDestination="home"){
  composable("home"){HomeScreen{nav.navigate(it)}}
  sections.forEach{s->composable(s.route){ModuleScreen(s)}}
  composable("search"){UtilityScreen("البحث الشامل","محرك بحث موحد قابل لربط قواعد البيانات لاحقًا.")}
  composable("favorites"){UtilityScreen("المفضلة","مكتبة موحدة للمحتوى المحفوظ.")}
  composable("progress"){UtilityScreen("التقدم","الورد، الختمة، الأهداف والإحصائيات.")}
  composable("settings"){UtilityScreen("الإعدادات","الخط، المظهر، القراءة، الخصوصية، النسخ الاحتياطي.")}
 }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(open:(String)->Unit){
 Scaffold(
  topBar={TopAppBar(title={Text("إسلامي",modifier=Modifier.fillMaxWidth(),textAlign=TextAlign.Right)},
   actions={IconButton({open("search")}){Icon(Icons.Default.Search,"البحث")}})},
  bottomBar={NavigationBar{
   NavigationBarItem(true,{},{Icon(Icons.Default.Home,null)},label={Text("الرئيسية")})
   NavigationBarItem(false,{open("favorites")},{Icon(Icons.Default.Star,null)},label={Text("المفضلة")})
   NavigationBarItem(false,{open("progress")},{Icon(Icons.Default.Insights,null)},label={Text("التقدم")})
   NavigationBarItem(false,{open("settings")},{Icon(Icons.Default.Settings,null)},label={Text("الإعدادات")})
  }}
 ){pad->
  LazyVerticalGrid(columns=GridCells.Fixed(2),modifier=Modifier.fillMaxSize().padding(pad).padding(12.dp),
   verticalArrangement=Arrangement.spacedBy(10.dp),horizontalArrangement=Arrangement.spacedBy(10.dp)){
   item(span={GridItemSpan(maxLineSpan)}){
    Card(Modifier.fillMaxWidth()){Column(Modifier.padding(18.dp)){
     Text("🌟 يومك مع إسلامي",style=MaterialTheme.typography.headlineSmall)
     Text("واجهة يومية جاهزة للربط بالمحتوى الحقيقي.")
     Spacer(Modifier.height(10.dp))
     Button({open("quran")},Modifier.fillMaxWidth()){Text("أكمل وردك")}
    }}
   }
   item(span={GridItemSpan(maxLineSpan)}){Text("الأقسام",style=MaterialTheme.typography.titleLarge)}
   items(sections){s->ElevatedCard(onClick={open(s.route)}){
    Column(Modifier.padding(14.dp).height(140.dp).fillMaxWidth(),horizontalAlignment=Alignment.CenterHorizontally,
     verticalArrangement=Arrangement.Center){
     Icon(s.icon,null,Modifier.size(34.dp));Spacer(Modifier.height(7.dp))
     Text(s.title,style=MaterialTheme.typography.titleMedium,textAlign=TextAlign.Center)
     Text(s.subtitle,style=MaterialTheme.typography.bodySmall,textAlign=TextAlign.Center)
    }
   }}
  }
 }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleScreen(s:Section){
 Scaffold(topBar={TopAppBar(title={Text(s.title)})}){pad->
  LazyColumn(Modifier.fillMaxSize().padding(pad).padding(16.dp),verticalArrangement=Arrangement.spacedBy(12.dp)){
   item{Icon(s.icon,null,Modifier.size(58.dp))}
   item{Text(s.title,style=MaterialTheme.typography.headlineMedium)}
   item{Text(s.subtitle)}
   item{Feature("🔎 البحث","بحث داخل محتوى القسم")}
   item{Feature("⭐ المفضلة","حفظ العناصر والرجوع إليها")}
   item{Feature("📌 آخر موضع","متابعة آخر موضع للقراءة")}
   item{Feature("📴 أوفلاين","العمل بالمحتوى المحلي دون الاعتماد على الويب")}
   item{Feature("📊 التقدم","أهداف وإنجاز حسب طبيعة القسم")}
   item{Feature("📤 المشاركة","مشاركة المحتوى")}
   item{Text("هذه نسخة التحقق من الهيكل والميزات، بدون أي بيانات دينية.")}
  }
 }
}

@Composable fun Feature(title:String,desc:String)=Card(Modifier.fillMaxWidth()){ListItem(
 headlineContent={Text(title)},supportingContent={Text(desc)})}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UtilityScreen(title:String,desc:String){
 Scaffold(topBar={TopAppBar(title={Text(title)})}){pad->
  Column(Modifier.fillMaxSize().padding(pad).padding(20.dp),horizontalAlignment=Alignment.CenterHorizontally){
   Spacer(Modifier.height(30.dp));Text(title,style=MaterialTheme.typography.headlineMedium)
   Spacer(Modifier.height(12.dp));Text(desc,textAlign=TextAlign.Center)
   Spacer(Modifier.height(24.dp))
   Text("لا توجد بيانات في هذه النسخة. هذه النسخة مخصصة لمراجعة الهيكل قبل إدخال بياناتك.")
  }
 }
}
