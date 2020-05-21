package vn.mrlongg71.koin_mvvm

class BookRepository(private val iApiService: IAPIService) {
     fun getAllBooks() = iApiService.getBooks()

}