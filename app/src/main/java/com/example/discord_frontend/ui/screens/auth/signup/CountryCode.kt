package com.example.discord_frontend.ui.screens.auth.signup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.discord_frontend.R
import com.example.discord_frontend.ui.theme.DiscordTheme
import com.example.discord_frontend.ui.components.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CountryCode(val code: String, val name: String)

open class CountryCodeViewModel : ViewModel() {
    val _countries = MutableStateFlow(listOf<CountryCode>())
    val countries: StateFlow<List<CountryCode>> = _countries

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            _countries.value = listOf(
                CountryCode("+1", "미국"),
                CountryCode("+7", "러시아"),
                CountryCode("+55", "브라질"),
                CountryCode("+91", "인도"),
                CountryCode("+86", "중국"),
                CountryCode("+27", "남아프리카공화국"),
                CountryCode("+52", "멕시코"),
                CountryCode("+966", "사우디아라비아"),
                CountryCode("+90", "터키"),
                CountryCode("+44", "영국"),
                CountryCode("+33", "프랑스"),
                CountryCode("+49", "독일"),
                CountryCode("+39", "이탈리아"),
                CountryCode("+81", "일본"),
                CountryCode("+82", "대한민국"),
                CountryCode("+62", "인도네시아"),
                CountryCode("+61", "호주"),
                CountryCode("+1", "캐나다"),
                CountryCode("+54", "아르헨티나")
            )
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun getFilteredCountries(): List<CountryCode> {
        val currentQuery = searchQuery.value
        return if (currentQuery.isEmpty()) {
            countries.value
        } else {
            countries.value.filter {
                it.name.contains(currentQuery, ignoreCase = true) ||
                        it.code.contains(currentQuery, ignoreCase = true)
            }
        }
    }

    fun onBackClicked() {
        TODO()
    }
}

@Composable
fun CountryCodePicker(navController: NavController){
    val viewModel = CountryCodeViewModel()
    val onCountrySelected: (CountryCode) -> Unit = {}
    CountryCodeSelectionScreen(viewModel, onCountrySelected, navController)
}

@Composable
fun CountryCodeSelectionScreen(
    viewModel: CountryCodeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onCountrySelected: (CountryCode) -> Unit,
    navController: NavController
) {
    val countries by viewModel.countries.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.padding(top = 13.dp))

        // 뒤로가기 버튼
        Box(modifier = Modifier.fillMaxWidth()) {
            BackButton(
                onClick = viewModel::onBackClicked,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )
        }

        /*  검색 필드
        Textfield 내에 기본 여백 존재하는 관계로 내부 여백은 조절 불가
         */
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.onSearchQueryChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(DiscordTheme.colors.inputFieldBackground),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_hint),
                    fontSize = DiscordTheme.typography.searchBar.fontSize,
                ) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = DiscordTheme.colors.inputFieldBackground,
                disabledContainerColor = DiscordTheme.colors.inputFieldBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            textStyle = DiscordTheme.typography.searchBar,
        )


        LazyColumn {
            items(viewModel.getFilteredCountries()) { country ->
                CountryCodeItem(country) {
                    onCountrySelected(country)
                }
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
fun CountryCodeItem(country: CountryCode, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = country.name,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        Text(
            text = country.code,
            modifier = Modifier.width(60.dp),
            textAlign = TextAlign.End
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountryCodeSelectionScreenPreview() {
    val previewViewModel = object : CountryCodeViewModel() {
        init {
            viewModelScope.launch {
                _countries.value = listOf(
                    CountryCode("+82", "대한민국"),
                    CountryCode("+1", "미국"),
                    CountryCode("+81", "일본"),
                    CountryCode("+86", "중국"),
                    CountryCode("+44", "영국")
                )
            }
        }
    }

    CountryCodeSelectionScreen(
        viewModel = previewViewModel,
        onCountrySelected = { /* Preview에서는 아무 동작 하지 않음 */ },
        navController = NavController(LocalContext.current)
    )
}